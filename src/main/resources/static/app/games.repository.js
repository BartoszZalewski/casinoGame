
(function () {
    'use strict';

    angular
        .module('app')
        .controller('GameRepository', GameRepository);

    GameRepository.$inject = ['$http'];

    function GameRepository($http) {
        var vm = this;

        vm.games = [];
        vm.getAll = getAll;
        vm.set = set;

        init();

        function init(){
            getAll();
            getAll;
        }

        function getAll(){
            var url = "/allGamesRepo";
            var bookingsPromise = $http.get(url);
            bookingsPromise.then(function(response){
                vm.games = response.data;
            });
        }

        function set(page){
            var nick = document.getElementById("username").innerHTML;
            var url ='/' +page + '/' + nick;
            window.location.replace(url);
        }
    }
})();
