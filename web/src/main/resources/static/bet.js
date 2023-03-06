(() => {
    document.addEventListener("DOMContentLoaded", () => {
        showBets('bet?page=0&size=20');

        function showBets(path) {
                fetch(path)
                .then( function(response) {
                    response.json().then(function(data) {
                        if (response.status === 200) {
                            updateTable(data)
                        } else {
                            var errorElement = document.getElementById('errorMessage')
                            errorElement.innerHTML = `<div>Request failed: ${data.message}</div>`;
                        }
                    })
                })
        }

        document.getElementById('search-form').addEventListener("click", () => {
            var errorElement = document.getElementById('errorMessage')
            var param = document.getElementById('search').value
            errorElement.innerHTML = '';
            var path = 'bet?game='+param+'&page=0&size=20';
            showBets(path);
        })

        document.getElementById('load-bets').addEventListener("click", () => {
        var path = 'bet/load';
        fetch(path)
            .then( function(response) {
                response.json().then(function(data) {
                    if (response.status === 200) {
                        showBets('bet?page=0&size=20');
                    }else {
                         var errorElement = document.getElementById('errorMessage')
                         errorElement.innerHTML = `<div>Request failed: ${data}</div>`;
                     }
                })
            })
    })

        document.getElementById('searchBet').addEventListener("click", () => {
            var errorElement = document.getElementById('errorMessage')
            var games = document.getElementById('gameId').value
            var client = document.getElementById('clientId').value
            var startDate = document.getElementById('startDate').value
            var endDate = document.getElementById('endDate').value
            errorElement.innerHTML = '';
            var path = 'bet?game='+games+'&clientId='+client+'&startDate='+startDate+'&endDate='+endDate+'&page=0&size=20';
            showBets(path);
        })

        function updateTable(data) {
            var table = document.getElementById('allBets')
            var rows = "<thead><tr><th scope=\"col\">#</th><th scope=\"col\">Game</th><th scope=\"col\">Client</th><th scope=\"col\">Num Bets</th><th scope=\"col\">Stake</th><th scope=\"col\">Returns</th><th scope=\"col\">Date</th></tr></thead>"
            let sn = 1;
            data.bets.forEach(element => {
                rows += `<tr><th scope=\"row\">${sn}</th><td>${element.game}</td><td>${element.clientId}</td><td>${element.numbets}</td><td>${element.stake}</td><td>${element.returns}</td><td>${element.betDate}</td></tr>`;
                sn++;
            });
            table.innerHTML = rows;
        }

        function loadClients() {
             let $select = $('#clientId');
             $select.append(`<option value="1"> Client 1 </option>`);
             $select.append(`<option value="2"> Client 2 </option>`);
        }

        function loadGames() {
          let $select = $('#gameId');
          $select.append(`<option value="baccarat"> Baccarat </option>`);
          $select.append(`<option value="blackjack"> Blackjack </option>`);
          $select.append(`<option value="roulette"> Roulette </option>`);
        }

     loadClients();
     loadGames();
    });
})();