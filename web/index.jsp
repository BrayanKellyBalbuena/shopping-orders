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
        <v-footer dark absolute>
          <v-card class="flex" flat tile>
            <v-card-actions class="grey darken-3 justify-center">
              <strong>Brayan Kelly 20181876</strong>  -
              <strong>Date {{ new Date() }} </strong>
            </v-card-actions>
          </v-card>
        </v-footer>
      </template>
    </v-app>
  </div>

  <script src="js/libs/vue.js"></script>
  <script src="js/libs/vuetify.js"></script>
  <script src="js/libs/axios.js"></script>
  </body>
</html>
