
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
        var divSize = 90;
        var defaultBoard = '{}';
        vm.board = "{}";
        vm.nextSpin = nextSpin;
        vm.set = set;
        vm.back = back;
        vm.getAllPlayers = getAllPlayers;
        vm.players = "{}";
        vm.jackpots = "{}";

        init();

         function init() {
             updateInfo();
             setBets();
             setDefaultBoard();
             setBoardImgs(defaultBoard);
             window.setTimeout(startLoop, 1000);
         }

        function getJackpots() {
            var url = "/allJackpots";
            var result = $http.get(url);
            result.then(function(response){
                vm.jackpots = response.data;
                setJackpots();
            });
        }

        function setJackpots() {
             var jack = vm.jackpots;
             var divs = '';
             for(var i = 0; i < jack.length; i++) {
                 var jackpot = jack[i];
                 var color = jackpot.name;
                 var value = Math.floor(jackpot.currentValue);
                 if(color === "bronze"){
                     color = 'brown';
                 }
                 divs += '<div style="text-align: center; float: left; background-color: '+color+'; font-size: '+divSize/4+'px; width: '+(divSize * 2)+'px ; height:'+divSize+'px; line-height: '+divSize+'px;">'+jackpot.name+': '+value+'</div>';
             }
             document.getElementById("jackpotsDiv").innerHTML = divs;
        }

        function updateInfo(){
             getAllPlayers();
             getJackpots();
        }

        function getAllPlayers() {
            var url = "/allPlayers";
            var result = $http.get(url);
            result.then(function(response){
                vm.players = response.data;
                setPlayersDiv();
            });
        }

        function startLoop() {
            myInterval = setInterval(updateInfo, iFrequency );
        }

        function setBets(){
            var bets = JSON.parse(document.getElementById("bets").innerHTML);
            document.getElementById("bets").innerHTML = '';
            var options = '';
            for(var i =0; i < bets.length; i++) {
                var bet = bets[i];
                options += '<option value="'+bet+'">'+bet+'</option>'
            }
            document.getElementById("betsSection").innerHTML += options;
        }

        function nextSpin(){
            var board = vm.board["board"];
            if(typeof board === "undefined") {
                board = defaultBoard;
            }
            var betsSection = document.getElementById("betsSection");
            var bet =  betsSection.options[betsSection.selectedIndex].value;
            var nick = document.getElementById("username").innerHTML;
            var url = "/nextSpin/" + nick + '/' + bet;
            var config = {
                headers : {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            };

            var result = $http.post(url, board, config);
            result.success(function(data, status, header, config){
                vm.board = data;
                updateCredits();
                spin();
                getJackpots();
            });

        }

        function spin(){
            var gameBoard = vm.board["board"];
            for(var i = 0; i < gameBoard.length; i++) {
                spinColumn(0, i);
            }
        }

        function fillRandom(id){
            var randomDivs = "";
            var gameBoard = vm.board["board"];
            var column = gameBoard[id];
            var size = getRandomInt(3, 7) * column.length;

            for(var i =0; i< size; i++){
                var value = Math.floor(Math.random() * 10);
                var img = '<img style="width: '+divSize+'px; height: '+divSize+'px" src="../images/' + value +'.png">';
                randomDivs += '<div style=" width: ' + (divSize) + 'px; height: ' + (divSize) + 'px;">'+img+'</div>';
            }

            for(var k = 0; k < column.length; k++){
                var value = column[k];
                var img = '<img style="width: '+divSize+'px; height: '+divSize+'px" src="../images/' + value +'.png">';
                randomDivs += '<div id="'+(id * gameBoard[0].length + k) + '" style=" width: ' + (divSize) + 'px; height: ' + (divSize) + 'px;">'+img+'</div>';
            }

            for(var j = 0; j< size; j++){
                var value = Math.floor(Math.random() * 10);
                var img = '<img style="width: '+divSize+'px; height: '+divSize+'px" src="../images/' + value +'.png">';
                randomDivs += '<div style=" width: ' + (divSize) + 'px; height: ' + (divSize) + 'px;">'+img+'</div>';
            }

            document.getElementById('column'+id).innerHTML = randomDivs;
            return size;
        }

        function spinColumn(count, columnId) {
            $('#column'+columnId).animate({
                top: -divSize
            }, 500, 'linear', function () {
                if (count === 0) {
                    var slot = fillRandom(columnId),
                        top = -slot * divSize,
                        time =  500;
                    $(this).css({
                        top: 0
                    }).animate({
                        top: top
                    },time, 'easeOutQuad')
                    setBoardInfo();
                    setLines();
                } else {
                    $(this).css({
                        top: 0
                    });
                    spinColumn(count - 1, columnId);
                }
            });
        }

        function setDefaultBoard() {
            var gameBoard = JSON.parse(document.getElementById("boardDiv").innerHTML);
            defaultBoard = gameBoard;
            createBoard(gameBoard);
        }

        function createBoard(gameBoard) {
            var columnDiv = '<div id="boardField" style="position: relative; width: ' + (divSize * gameBoard.length) + 'px; height: ' + (divSize * gameBoard[0].length) + 'px;">';
            for (var col = 0; col < gameBoard.length; col++) {
                columnDiv += '<div id = "column' + col + '" style="position: relative; width: ' + (divSize) + 'px; height: ' + (divSize * gameBoard[0].length) + 'px; float: left;">';
                for (var row = 0; row < gameBoard[col].length; row++) {
                    columnDiv += '<div id="'+(col * gameBoard[0].length + row) + '" style="width: ' + (divSize) + 'px; height: ' + (divSize) + 'px;"></div>';
                }
                columnDiv += '</div>';
            }
            columnDiv += '</div>';
            document.getElementById('boardDiv').innerHTML = columnDiv;

        }

        function setBoardImgs(gameBoard) {
            for(var col = 0; col < gameBoard.length; col++) {
               for(var row = 0; row < gameBoard[col].length; row++) {
                  image(gameBoard[col][row], (col * gameBoard[0].length + row));
                }
            }
        }

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        function dist(x, y) {
            return (x+y)/2;
        }

        function setLines(){
            var gameBoard = vm.board["board"];
            var events = vm.board["events"];
            var svgObj = '';
            var width = divSize * gameBoard.length;
            var height = divSize * gameBoard[0].length;
            var svg = document.getElementById('svgDiv');
            svg.setAttribute("height", height);
            svg.setAttribute("width", width);
            svgObj += '';
            document.getElementById('svgDiv').innerHTML = '';
            for (var i = 0; i < events.length; i++) {
                var event = events[i];
                var name = event.name;
                var symbolsInLine = event.symbolsInLine;
                var line = event.line;
                var lineValue = event.points;
                var points = line.points;
                var r = getRandomInt(100, 200);
                var g = getRandomInt(100, 200);
                var b = getRandomInt(100, 200);
                for (var j = 0; j < symbolsInLine.length; j++) {
                    var first = points[symbolsInLine[j]];
                    var id = (first.x * gameBoard[0].length + first.y);
                    rotateAnimation(id);
                    if (j !== symbolsInLine.length - 1 && name === "MatchedLine") {
                        var next = points[symbolsInLine[j + 1]];
                        var x1 = divSize / 2 + first.x * divSize;
                        var x2 = divSize / 2 + next.x * divSize;
                        var y1 = divSize / 2 + first.y * divSize;
                        var y2 = divSize / 2 + next.y * divSize;
                        svgObj += '<line x1="' + x1 + '" y1="' + y1 + '" x2="' + x2 + '" y2="' + y2 + '" stroke="red" style="stroke:rgb(' + r + ',' + g + ',' + b + ');stroke-width:5"/>';
                        if (j === 0) {
                            svgObj += '<text fill="rgb(' + r + ',' + g + ',' + b + ')" font-size="' + divSize / 2 + '" font-family="Verdana" x="' + dist(x1, x2) + '" y="' + dist(y1, y2) + '">' + lineValue + '</text>';
                        }
                        document.getElementById('svgDiv').innerHTML += svgObj;
                    }
                }

            }
        }

        function setPlayersDiv() {
            var players = vm.players;
            var divs = '';
            var empty = '';
            for (var i = 0; i < players.length; i++) {
                var player = players[i];
                var color = player.name;
                var value = player.credits;
                divs += '<div style="text-align: center; border: solid blue;color: white; background-color: black; font-size: ' + divSize / 8 + 'px; width: ' + (divSize * 4) + 'px ; height:' + divSize/2 + 'px; line-height: ' + divSize/2 + 'px;">' + player.nickName + ' with ' + value + ' credits</div>';
                empty += '<div style="text-align: center; color: white; font-size: ' + divSize / 8 + 'px; width: ' + (divSize * 4) + 'px ; height:' + divSize/2 + 'px; line-height: ' + divSize/2 + 'px;"></div>';

            }
            document.getElementById("playersDiv").innerHTML = divs;
            document.getElementById("spaceDiv").innerHTML = empty;

        }

        function setBoardInfo() {
            var infoDiv = '';
            var win = vm.board["win"];
            var lose = vm.board["lose"];
            if(win > 0) {
                infoDiv = '<h1 style="color: green; font-size: '+divSize/2+'px"> win: ' + win +'</h1>';
            }
            else {
                infoDiv = '<h1 style="color: red; font-size: '+divSize/2+'px"> lose </h1>';
            }

            document.getElementById('info').innerHTML = infoDiv;
        }

        function image(imgId, id) {
           var img = '<img style="width: '+divSize+'px; height: '+divSize+'px" src="../images/' + imgId +'.png"></img>';
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
           var url = "/gamesPanel/" + nick;
           window.location.replace(url);
        }

        function rotateAnimation(el){
            var elem = document.getElementById(el);
            elem.classList.add('rotate');
        }


    }
})();
