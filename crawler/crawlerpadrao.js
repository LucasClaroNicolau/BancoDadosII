var request = require("request");
var cheerio = require("cheerio");
var mysql = require('./mysql.js');
var arq = '';
var urls = ['https://twitter.com/search?q=acertei%20enem%202018&src=typd',
			'https://twitter.com/search?l=pt&q=terceirao%202018&src=spxr',
			'https://twitter.com/search?q=terceir%C3%A3o%20formatura&src=typd', 
			'https://twitter.com/search?l=pt&q=terceir%C3%A3o%20formatura&src=typd',
			'https://twitter.com/search?q=faltam%20para%20formatura%20terceir%C3%A3o&src=typd',
			'https://twitter.com/search?q=formatura%20terceir%C3%A3o%202018&src=typd',
			'https://twitter.com/search?q=sentir%20falta%20terceir%C3%A3o%202018&src=typd',
			'https://twitter.com/search?q=saudade%20terceir%C3%A3o%202018&src=typd',
			'https://twitter.com/search?q=irei%20fazer%20enem%202018&src=typd',
			'https://twitter.com/search?q=ancioso%20prova%20enem%202018&src=typd',
			'https://twitter.com/search?q=tenho%20que%20estudar%20enem%202018&src=typd',
			'https://twitter.com/search?q=fiz%20enem%202018&src=typd',
			'https://twitter.com/search?q=domingo%20vou%20enem%202018&src=typd',
			'https://twitter.com/search?q=terei%20enem%202018&src=typd',
			'https://twitter.com/search?q=tive%20enem%202018&src=typd',
			'https://twitter.com/search?q=errei%20enem%202018&src=typd',
			'https://twitter.com/search?q=acertei%20enem%202018&src=typd'] ;


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
		
		console.log(date)
		
		
        var data = [usuario, tweet,date];
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