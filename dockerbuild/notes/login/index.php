<?php
include_once("../DBasequery.php");
if(isset($_POST['login'])){
    $token = hash('md5', (string)time().$_POST['login'].$_POST['password']);
    $HOST_NAME = 'localhost';
    $USER_NAME = 'multinote';
    $USER_PASSWORD = 'multinotepassword';
    $DB_NAME = 'multinote';

    $result = array();

    $db = new Dbase();
    $res = $db->connect($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME);
    if(is_bool($res)){
        $result['connection'] = true;
        $res = $db->check_password($_POST['login'], $_POST['password']);
        if(is_bool($res) and $res){
            $result['answer'] = 'Autorized';
            $result['token'] = $token;
	    $string = file_get_contents("data.json");
	    $data = json_decode($string, true);
	    $data[$_POST['login']] = $token;
	    $result[] = $data;
	    $json = json_encode($data);
	    $file = fopen('data.json', 'w');
	    $write = fwrite($file,$json);
	    $result[] = $write;
	    fclose($file);
        } else if(is_bool($res)){
            $result['answer'] = 'NOT Autorized';
            $result['token'] = '-';
        } else{
            $result['answer'] = 'Error';
            $result['token'] = '-';
        }
    } else {
        $result['connection'] = false;
	$result['answer'] = 'Error';
    }
    echo json_encode($result, JSON_FORCE_OBJECT);
} else {
  echo json_encode(["answer" => "Error"], JSON_FORCE_OBJECT);
}
?>
