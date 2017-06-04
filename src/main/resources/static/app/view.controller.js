
(function () {
    'use strict';

    angular
        .module('app')
        .controller('ViewController', ViewController);

    ViewController.$inject = ['$http'];

    function ViewController($http) {
        var vm = this;
        vm.play = play;

        function play(){
            var x = document.getElementById("myText").value;
            var url = "/game/" + x;
            window.location.replace(url);
        }
    }
})();
