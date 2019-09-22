new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data () {
        return {
            dialog:false,
            singleSelect: true,
            search: '',
            headers: [
                {
                    align: 'left',
                    sortable: false,
                    value: 'name',
                },
                { text: 'Id', value: 'id' },
                { text: 'Client Name', value: 'clientName' },
                { text: 'Product', value: 'product.name' },
                {text: 'Price', value: 'product.price'},
                {text: 'Quantity', value: 'quantity'},
                {text: 'SubTotal', value: 'subTotal'},
                { text: 'OrderDate', value: 'orderDate' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            products:[],
            orders: [],
            clients:[],
            selectedClientId: {},
            selectedProductId: {},
            selectedClient:{},
            selectedProduct:{},
            editedOrder: {
                id: 0,
                clientName: '',
                product: {id: 0, name: '', price: 0.0},
                quantity: 0.0,
                subTotal: 0.0,
                orderDate:  ''
            },
            defaultOrder: {
                id: 0,
                clientName: '',
                product: {id: 0, name: '', price: 0.0},
                quantity: 0.0,
                subTotal: 0.0,
                orderDate:  ''
            },
            editedIndex: -1,
            API_ORDERS: "./api/shopping-orders",
            API_PRODUCTS: "./api/products",
            API_CLIENTS: "./api/clients"
        }
    },

    computed: {
        formTitle() {
            return this.editedIndex === -1 ? 'New Order' : 'Edit Order'
        }
    },

    watch: {
        dialog (val) {
            val || this.close();
        },

        selectedProductId(val) {
            this.selectedProduct = this.products.find(p => p.id === val);
            this.editedOrder.product = this.selectedProduct;
        },

        selectedClientId(val) {
            this.selectedClient = this.clients.find(c => c.id === val);
        }

    },

    mounted: function(){
        let $vm = this;
        this.getAllMembers($vm);
    },

    methods:{
        getAllMembers: function(vm){
            axios.get(this.API_ORDERS)
                .then(function(response){
                    vm.orders = response.data;
                    console.log({data: vm.orders})
                });

            axios.get(this.API_PRODUCTS).then( (resp) => {
                vm.products = resp.data;
            }).catch((error) => {
                console.error(error);
            });

            axios.get(this.API_CLIENTS).then((resp) => {
                vm.clients = resp.data;
            }).catch((error) => {
                console.error(error);
            });
        },

        editOrder: function(order) {
            this.editedIndex = this.orders.indexOf(order);
            this.editedOrder = Object.assign({}, order);
            this.selectedClientId = order.clientId;
            this.selectedProductId = order.product.id;
            this.dialog = true;
        },

        deleteOrder: function(order) {
            const id = this.orders.find(c => c.id === order.id).id;
            if(confirm('Are you sure want to delete') ){
                axios.delete(this.API_ORDERS , {
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
                this.editedOrder = Object.assign({}, this.defaultOrder);
                this.editedIndex = -1
            }, 300)
        },

        save () {
            if (this.editedIndex > -1) {

                let currentClient = this.clients.find( c => c.id === this.selectedClientId);
                axios.put(this.API_ORDERS,{
                    id: this.editedOrder.id,
                    clientId: currentClient.id,
                    clientName: currentClient.name,
                    product: this.selectedProduct,
                    quantity: this.editedOrder.quantity,
                    subTotal: this.selectedProduct.price * this.editedOrder.quantity,
                    orderDate:  new Date().toDateString()}

                ).then( (response) => {
                    this.getAllMembers(this)
                }).catch((error) => {
                    console.error(error)
                });

            } else {

                let currentClient = this.clients.find( c => c.id === this.selectedClientId);
                axios.post(this.API_ORDERS,{
                    clientId: currentClient.id,
                    clientName: currentClient.name,
                    product: this.selectedProduct,
                    quantity: this.editedOrder.quantity,
                    subTotal: this.selectedProduct.price * this.editedOrder.quantity,
                    orderDate:  new Date().toDateString()}
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