<%--
  Created by IntelliJ IDEA.
  User: scrip
  Date: 9/15/2019
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/@mdi/font@3.x/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="css/vuetify.min.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
  </head>
  <body>
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <a class="navbar-brand" href="#">Logo</a>
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="clients.jsp">Clients</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="orders.jsp">Orders</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="products.jsp">Products</a>
      </li>
    </ul>
  </nav>
  <div id="app">
    <v-app>

      <template>
        <v-data-table
                :headers="headers"
                :items="clients"
                :search="search"
                sort-by="id"
                class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat color="white">
              <v-toolbar-title>Clients</v-toolbar-title>
              <v-divider
                      class="mx-4"
                      inset
                      vertical
              ></v-divider>
              <div class="flex-grow-1"></div>
                <v-text-field
                        v-model="search"
                        append-icon="search"
                        label="Search"
                        single-line
                        hide-details
                        class="col-md-4"
                ></v-text-field>
              <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on }">
                  <v-btn color="primary" dark class="mb-2" v-on="on">New Item</v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="headline">{{ formTitle }}</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6" hidden>
                          <v-text-field v-model="editedClient.id" label="Client Id"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedClient.name" label="Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedClient.lastName" label="Last Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedClient.identificationCard" label="Identification Card"></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <v-card-actions>
                    <div class="flex-grow-1"></div>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editClient(item)"
            >
              edit
            </v-icon>
            <v-icon
                    small
                    @click="deleteClient(item)"
            >
              delete
            </v-icon>
          </template>

        </v-data-table>
      </template>
    </v-app>
  </div>

  <script src="js/libs/vue.js"></script>
  <script src="js/libs/vuetify.js"></script>
  <script src="js/libs/axios.js"></script>
  <script>
    new Vue({
      el: '#app',
      vuetify: new Vuetify(),
      data () {
        return {
          dialog:false,
          singleSelect: true,
          search: '',
          headers: [
            { text: 'Id', value: 'id' },
            { text: 'Name', value: 'name' },
            { text: 'LastName', value: 'lastName' },
            { text: 'IdentificationCard', value: 'identificationCard' },
            { text: 'Actions', value: 'action', sortable: false }
          ],
          clients: [],
          editedClient: {
            id: 0,
            name: '',
            lastName: '',
              identificationCard: ''
          },
          defaultClient: {
            id: 0,
            name: '',
            lastName: '',
            identificationCard: ''
          },
          editedIndex: -1,
          API_PATH: "./api/clients"
        }
      },

      computed: {
        formTitle() {
          return this.editedIndex === -1 ? 'New Client' : 'EditClient'
        }
      },

      watch: {
        dialog (val) {
          val || this.close()
        }
      },

      mounted: function(){
        let $vm = this;
        this.getAllMembers($vm);
      },

      methods:{
        getAllMembers: function(vm){
          axios.get("./api/clients")
                  .then(function(response){
                    vm.clients = response.data;
                  });
        },

        editClient: function(client) {
          this.editedIndex = this.clients.indexOf(client);
          this.editedClient = Object.assign({}, client);
          this.dialog = true;
            console.log(this.editedClient.identificationCard);
        },

        deleteClient: function(client) {
          const id = this.clients.find(c => c.id === client.id).id;
         if(confirm('Are you sure want to delete') ){
           axios.delete(this.API_PATH , {
             params: {
               id: id
             }
           } ).then( (response) => {
             this.clients.splice(
                     this.clients.indexOf( c => c.id === client.id), 1);
           }).catch( (error) => {
             alert(error)
           })
         }
        },

        close () {
          this.dialog = false;
          setTimeout(() => {
            this.editedClient = Object.assign({}, this.defaultClient);
            this.editedIndex = -1
          }, 300)
        },

        save () {
          let $v = this;
          if (this.editedIndex > -1) {
              axios.put(this.API_PATH,{
                  id: this.editedClient.id,
                  name: this.editedClient.name,
                  lastName: this.editedClient.lastName,
                  identificationCard : this.editedClient.identificationCard}
              ).then( (response) => {
                  let indexOf = this.clients.indexOf(c => c.id === this.editedClient.id);
                  this.clients.splice(indexOf,1,this.editedClient);
              }).catch((error) => {
                console.error(error)
              });

          } else {
            axios.post(this.API_PATH,{
                name: this.editedClient.name,
                lastName: this.editedClient.lastName,
                identificationCard : this.editedClient.identificationCard}
            ).then( (response) => {
                this.editedClient.id = JSON.parse(response.data);
                this.clients.push(this.editedClient);
                console.log(response.data)
            }).catch((error) => {
              console.error(error)
            })

          }
          this.close()
        },
      }
    });
  </script>
  </body>
</html>
