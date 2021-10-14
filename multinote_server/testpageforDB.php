<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DataBaseTestPage</title>
</head>
<body>
<h2>Тестирование функций для регистрации и авторизации</h2>
<p>
    <?php
        include_once("DBasequery.php");
        $HOST_NAME = 'localhost';
        $USER_NAME = 'multinote';
        $USER_PASSWORD = 'multinotepassword';
        $DB_NAME = 'multinote';

        $db = new Dbase();
        $res = $db->connect($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME);
        echo "<b>Проверка подключения к БД -></b> ";
        if(is_bool($res)){
            echo "Успешное подключение<br>";
        } else{
            echo "Подключение к БД невоможно<br>";
        }

        echo "<b>Тестирование корректности пароля</b> -> <br>";
	$login = "user";
	$pass = "password";

	$func_ans = $db->check_password($login, $pass);
	echo "login: ".$login." password: ".$pass."<br>";
	
	function test($func_ans, $return_type, $answer){
		if(gettype($func_ans) == $return_type and $func_ans==$answer){
                	echo "test passed<br>";
       		} else {
			echo "type of result: ".gettype($func_ans)." result:".$func_ans;
		}
	}
	
	test($func_ans, 'boolean', true);

	$pass = "password1";
	$func_ans = $db->check_password($login, $pass);
	echo "login: ".$login." password: ".$pass."<br>";
        test($func_ans, 'boolean', false);

	$login = "mylogin";
	$func_ans = $db->check_password($login, $pass);
        echo "login: ".$login." password: ".$pass."<br>";
        test($func_ans, 'boolean', false);	
        echo "<br>";
	
        echo "<b>Тестирование добавления нового пользователя</b><br>";
	$func_ans = $db->add_user("user4", "password", "user4@example.com");
	echo "login: user4<br>";
	test($func_ans, 'boolean', true);
	echo "<br>";
	
	echo "<b>Тестирование удаления пользователя</b><br>";
	echo "login: user4<br>";
	$func_ans = $db->delete_user("user4");	
        test($func_ans, 'boolean', true);

        
	echo "<b>Тестирование существования логина</b><br>";
	$login = "user";
	echo "login: ".$login."<br>";
	$func_ans = $db->check_login($login);
	test($func_ans, 'boolean', true);
	
	$login = "user1";
        echo "login: ".$login."<br>";
        $func_ans = $db->check_login($login);
        test($func_ans, 'boolean', false);

	$login = "mylogin";
        echo "login: ".$login."<br>";
        $func_ans = $db->check_login($login);
        test($func_ans, 'boolean', false);
	
        echo "<b>Тестирование существования эмайла</b><br>";
	$email = "user@example.com";
	echo "email: ".$email."<br>";
        $func_ans = $db->check_email($email);
        test($func_ans, 'boolean', false);
	
	$email = "email3@example.com";
        echo "email: ".$email."<br>";
        $func_ans = $db->check_email($email);
        test($func_ans, 'boolean', true);
	   
 ?>
</p>
<h2>Тестирование функций для заметок</h2>
<p>
    <?php
	function test2($func_ans, $type){
		if (gettype($func_ans)==$type){
			echo "test passed";
		} else {
		echo "test not passed. Required type result: ".$type." got ".gettype($func_ans);  
	}}

	function test_note($func_ans, $id){
		if (gettype($func_ans)=='array'){
			if(array_key_exists('id', $func_ans)){
			if($func_ans['id']==$id){
				return "Test passed";
		}else{
			return "Test not passed. Wrong id.";
		}	
		}else{
			return "Test not passed. Key id not exists.";
		}
	} 
		return "Test not passed. Got not array";
	}
	echo "<b>Тестирование получения заметки</b><br>";
	$id = 1;
	$note = $db->get_note_by_id($id);
	echo "id = $id<br>";
	test2($note, 'array');
	echo "<br>".test_note($note, $id);
	echo "<br>".var_export($note, true);

	$id = 100;
	$note = $db->get_note_by_id($id);
        echo "<br>id = $id<br>";
        test2($note, 'boolean');
        echo "<br>".test_note($note, $id);
        echo "<br>".var_export($note, true);

	echo "<br><b>Тестирование получения информации о статуса изменения</b><br>";
	$id_note = 1;
	$is_modified = $db->is_modified($id_note);
	echo "<br>id = $id_note<br>";
	test($is_modified, 'string', false);
	
	$id_note = 12;
        $is_modified = $db->is_modified($id_note);
        echo "<br>id = $id_note<br>";
        test($is_modified, 'boolean', false);
  
	$id_note = 100;
        $is_modified = $db->is_modified($id_note);
        echo "<br>id = $id_note<br>";
        test($is_modified,'boolean', false); 

	$id_note = 2;
        $is_modified = $db->is_modified($id_note);
        echo "<br>id = $id_note<br>";
        test($is_modified, 'string', true);

	echo "<br><b>Тестирование изменения статуса заметки</b><br>";
	$id_note = 1;
	$func_ans = $db->set_is_modified($id_note, 1);
	echo "<br>id = $id_note<br>";
	test($func_ans, 'boolean', true);

	$id_note = 2;
        $func_ans = $db->set_is_modified($id_note, true);
        echo "<br>id = $id_note<br>";
        test($func_ans, 'boolean', true);

	$id_note = 100;
        $func_ans = $db->set_is_modified($id_note, true);
        echo "<br>id = $id_note<br>";
        test($func_ans, 'boolean', false);

		
	echo "<br><b>Тестирование обновления заметки</b><br>";

	$id_note = 1;
	$text = "lolo";
	$user = "user";
	$func_ans = $db->update_note($id_note, $text, $user);
	test($func_ans, 'boolean', true);

	$id_note = 200;
        $func_ans = $db->update_note($id_note, $text, $user);
        test($func_ans, 'boolean', false);

	echo "<br><b>Тестирование проверки доступа</b><br>";
	
	$id_note = 2;
	$login = "user";
	$func_ans = $db->check_access($id_note, $login);
	test($func_ans, 'boolean', false);

	$id_note = 1;
        $login = "user2";
        $func_ans = $db->check_access($id_note, $login);
        test($func_ans, 'boolean', true);
	
	echo "<br><b>Тестирование добавления доступа</b><br>";
	
	$login = 'user4';
	$func_ans = $db->add_access($id_note, $login);
	test($func_ans, 'boolean', true);

	$login = 'user5';
        $func_ans = $db->add_access($id_note, $login);
        test($func_ans, 'boolean', true);

	echo "<br><b>Тестирование получения массива айди заметок доступа</b><br>";
	
	$login = 'lala';
	$func_ans = $db->get_notes_access($login);
	test($func_ans, 'boolean', false);

	$login = 'user';
	$func_ans = $db->get_notes_access($login);
        test2($func_ans, 'array');
	echo "<br>".var_export($func_ans);
	
	echo "<br><b>Тестирование получения заметок доступа</b><br>";
	
	$func_ans = $db->get_notes_user_has_access($login);
	test2($func_ans, 'array');
        echo "<br>".var_export($func_ans);
	
	$login = 'lolo';
	$func_ans = $db->get_notes_user_has_access($login);
        test($func_ans, 'boolean', false);

	echo "<br><b>Тестирование получения всех заметок</b><br>";

	$login = 'user';
	$func_ans = $db->get_notes($login);
        test2($func_ans, 'array');
        echo "<br>".var_export($func_ans);

        $login = 'lolo';
        $func_ans = $db->get_notes($login);
        test(is_null(array_pop($func_ans)), 'boolean', true);
	


?>
</p>
</body>
</html>
