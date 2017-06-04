
(function () {
    'use strict';

    angular
        .module('app')
        .controller('GameController', GameController);

    GameController.$inject = ['$http'];

    function GameController($http) {
        var vm = this;
        var iFrequency = 5000;
        var myInterval = 0;

        vm.board = "{}";
        vm.nextSpin = nextSpin;
        vm.set = set;
        vm.back = back;
        vm.getAllPlayers = getAllPlayers;
        vm.players = "{}";

        init();

         function init(){
            window.setTimeout(startLoop, 1000);
         }

        function getAllPlayers() {
            var url = "/allPlayers";
            var result = $http.get(url);
            result.then(function(response){
                vm.players = response.data;
            });
        }

        function startLoop() {
            myInterval = setInterval(getAllPlayers, iFrequency );
        }

        function nextSpin(){
            var bet = 1;
            var nick = document.getElementById("username").innerHTML;
            var url = "/nextSpin/" + nick + '/' + bet;
            var result = $http.get(url);
            result.then(function(response){
                vm.board = response.data;
                updateCredits();
                setBoard();
                setBoardImgs();
                setBoardInfo();
                setLines();
            });

        }

        function setBoard() {
        var divSize = 100;
            var bigDiv;
            var gameBoard = vm.board["board"];
            var columnDiv = '<div id="boardField" style=""position: relative; z-index:-1; width: '+(divSize * gameBoard.length)+'px; height: '+(divSize * gameBoard[0].length)+'px; background-color: #ccc; text-align:center;">';
            for(var col = 0; col < gameBoard.length; col++) {
             columnDiv += '<div style="z-index:-1; width: '+(divSize)+'px; height: '+(divSize * gameBoard[0].length)+'px; float: left;">';
                for(var row = 0; row < gameBoard[col].length; row++) {
                    columnDiv+='<div id="'+(col * gameBoard[0].length + row)+'" style="z-index:-1; width: '+(divSize)+'px; height: '+(divSize)+'px;"></div>';
                }
                columnDiv += '</div>';
            }
            columnDiv += '</div>'
            document.getElementById('boardDiv').innerHTML = columnDiv;
        }

        function setBoardImgs() {
            var gameBoard = vm.board["board"];
            for(var col = 0; col < gameBoard.length; col++) {
               for(var row = 0; row < gameBoard[col].length; row++) {
                  image(gameBoard[col][row], (col * gameBoard[0].length + row));
                }
            }
        }

        function setLines(){
            var divSize = 100;
            var bigDiv;
            var gameBoard = vm.board["board"];
            var events = vm.board["events"];
            var svgObj = '';
            var width = divSize * gameBoard.length;
            var height = divSize * gameBoard[0].length;
            svgObj += '<svg width="'+width+'" height="'+height+'" style="position: fixed;">';
            for (var i = 0; i < events.length; i++) {
                    var event = events[i];
                    var name = event.name;
                    var symbolsInLine = event.symbolsInLine;
                    var line = event.lineDefinition;
                    var points = line.pointses;
                    var first = points[0];
                    var last = points[symbolsInLine - 1];
                    var x1 = divSize/2 + first.x * divSize;
                    var x2 = divSize/2 + last.x * divSize;
                    var y1 = divSize/2 + first.y * divSize;
                    var y2 = divSize/2 + last.y * divSize;
                    svgObj += '<line x1="'+x1+'" y1="'+y1+'" x2="'+x2+'" y2="'+y2+'" stroke="red" style="z-index:2; stroke:rgb(255,0,0);stroke-width:5"/>';
            }
            svgObj += '</svg>';
            var divValue = document.getElementById('boardField').innerHTML;
            document.getElementById('boardField').innerHTML = svgObj + divValue;
        }

        function setBoardInfo() {
            var infoDiv = '';
            var win = vm.board["win"];
            var lose = vm.board["lose"];
            infoDiv += '<h1 style="color: green; font-size: 100px"> win: ' + win +'</h1>';
            infoDiv += '<h1 style="color: red; font-size: 100px"> lose: ' + lose +'</h1>';
            document.getElementById('info').innerHTML = infoDiv;
        }

        function image(imgId, id) {
           var img = '<img style="z-index:-1;" src="../images/' + imgId +'.png"></img>';
           document.getElementById(id).innerHTML=img;
         }

        function set(page){
            window.location.replace(page);
        }

        function updateCredits(){
            var credits = parseInt(document.getElementById("credits").innerHTML);
            var win = parseInt(vm.board["win"]);
            var lose = parseInt(vm.board["lose"]);
            document.getElementById("credits").innerHTML = credits - lose + win;
        }

        function back(){
           var nick = document.getElementById("username").innerHTML;
           var url = "/game/" + nick;
           window.location.replace(url);
        }
    }
})();
