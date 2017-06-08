
(function () {
    'use strict';

    angular
        .module('app')
        .controller('ViewController', ViewController);

    ViewController.$inject = ['$http'];

    function ViewController() {
        var vm = this;
        vm.play = play;

        function play(){
            var x = document.getElementById("myText").value;
            var url = "/gamesPanel/" + x;
            window.location.replace(url);
        }
    }
})();
