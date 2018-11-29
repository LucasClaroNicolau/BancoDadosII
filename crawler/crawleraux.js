var fs = require('fs');
var request = require("request");
var cheerio = require("cheerio");
var mysql = require('./mysql.js');
var arq = '';


var content = fs.readFileSync("./parametro.txt" , "utf8", function(err, data){
	if(err){
		return console.log("Erro ao ler arquivo");
	}
	conteudo = data
});
var urls = ['https://twitter.com/search?q='+ (content.split(' ').join('%20')) +'&src=typd'];
console.log(urls);
mysql.conectar(); 

for(var i=0; i<urls.length; i++){
  request(urls[i], function (error, response, html) {
    if (!error && response.statusCode == 200) {
      var $ = cheerio.load(html);
	  
      var arquivos = [];
	 	  
      $('div.content').each(function () {
        var usuario = $(this).find('.username').text();
        var tweet = $(this).find('.tweet-text').text();		
		var date = $(this).find('.time a').attr('title');

        var data = [usuario, tweet, date];
        arquivos.push(data);
      });

      if(arquivos.length > 0){
        mysql.addRows(arquivos);
      }

    }else {
      console.log("Um erro ocorreu");
    }
  });
}