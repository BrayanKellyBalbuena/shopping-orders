new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data () {
        return {
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
                {text: 'SubT otal', value: 'subTotal'},
                { text: 'OrderDate', value: 'orderDate' },
            ],
            orders: []
        }
    },
    mounted: function(){
        let $vm = this;
        this.getAllMembers($vm);
    },

    methods:{
        getAllMembers: function(vm){
            axios.get("./api/shopping-orders")
                .then(function(response){
                    vm.orders = response.data;
                    console.log({data: vm.orders})
                });
        }
    }
})