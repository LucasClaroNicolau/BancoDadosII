const mysql = require('mysql');
const connection = mysql.createConnection({
    host: 'localhost',
    port: 3306,
    user: 'root',
    password: 'comp12',
    database: 'bigdata'
});

function createTable(conn) {

    const sql = "CREATE TABLE IF NOT EXISTS leads (\n" +
        "id int NOT NULL AUTO_INCREMENT,\n" +
		"username varchar(150)  NULL,\n" +
		"tweet varchar(700) NULL,\n" +
		"date varchar(30) NULL ,\n" +
		"PRIMARY KEY (id),\n" +
		"UNIQUE INDEX `tweet_UNIQUE` (`tweet` ASC) VISIBLE);";

    conn.query(sql, function (error, results, fields) {
        if (error) return console.log('',error);
        console.log('criou a tabela!');
    });
}

exports.addRows = function(data) {
    const sql = "INSERT INTO leads(username,tweet,date) VALUES ?";
    const values = [
        data
    ];
    connection.query(sql, values, function (error, results, fields) {
        if (error){
            
            return console.log('', error);
        }
    }).end();
}

exports.conectar = function(){
    connection.connect(function (err) {
        if (err) return console.log('',err);
        console.log('conectou!');

        createTable(connection);
    })
}