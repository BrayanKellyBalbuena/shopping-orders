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
                { text: 'Price', value: 'price' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            products: [],
            editedProduct: {
                id: 0,
                name: '',
                price: 0.0,
            },
            defaultProduct: {
                id: 0,
                name: '',
                price: 0.0,
            },
            editedIndex: -1,
            API_PATH: "./api/products"
        }
    },

    computed: {
        formTitle() {
            return this.editedIndex === -1 ? 'New Product' : 'Edit Product'
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
                    vm.products = response.data;
                });
        },

        editProduct: function(products) {
            this.editedIndex = this.products.indexOf(products);
            this.editedProduct = Object.assign({}, products);
            this.dialog = true;
        },

        deleteProduct: function(product) {
            const id = this.products.find(c => c.id === product.id).id;
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
                this.editedProduct = Object.assign({}, this.defaultProduct);
                this.editedIndex = -1
            }, 300)
        },

        save () {
            if (this.editedIndex > -1) {
                axios.put(this.API_PATH,{
                    id: this.editedProduct.id,
                    name: this.editedProduct.name,
                    price: this.editedProduct.price}
                ).then( (response) => {
                    this.getAllMembers(this)
                }).catch((error) => {
                    console.error(error)
                });

            } else {
                axios.post(this.API_PATH,{
                    name: this.editedProduct.name,
                    price: this.editedProduct.price}
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