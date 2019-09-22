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
                {text: 'Created Date', value: 'createdDate'},
                { text: 'Actions', value: 'action', sortable: false }
            ],
            clients: [],
            editedClient: {
                id: 0,
                name: '',
                lastName: '',
                identificationCard: '',
                createdDate: ''
            },
            defaultClient: {
                id: 0,
                name: '',
                lastName: '',
                identificationCard: '',
                createdDate: ''
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
            axios.get(this.API_PATH)
                .then(function(response){
                    vm.clients = response.data;
                });
        },

        editClient: function(client) {
            this.editedIndex = this.clients.indexOf(client);
            this.editedClient = Object.assign({}, client);
            this.dialog = true;
        },

        deleteClient: function(client) {
            const id = this.clients.find(c => c.id === client.id).id;
            if(confirm('Are you sure want to delete') ){
                axios.delete(this.API_PATH , {
                    params: {
                        id: id
                    }
                } ).then( (response) => {
                    this.getAllMembers(this)
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
            if (this.editedIndex > -1) {
                axios.put(this.API_PATH,{
                    id: this.editedClient.id,
                    name: this.editedClient.name,
                    lastName: this.editedClient.lastName,
                    identificationCard : this.editedClient.identificationCard}
                ).then( (response) => {
                    this.getAllMembers(this)
                }).catch((error) => {
                    console.error(error)
                });

            } else {
                axios.post(this.API_PATH,{
                    name: this.editedClient.name,
                    lastName: this.editedClient.lastName,
                    identificationCard : this.editedClient.identificationCard,
                    createdDate: new Date().toDateString()}
                ).then( (response) => {
                    this.getAllMembers(this)
                }).catch((error) => {
                    console.error(error)
                })

            }
            this.close()
        },
    }
});