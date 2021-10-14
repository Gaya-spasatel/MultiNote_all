<?php 
include_once("../DBasequery.php");
if(isset($_POST['reg_login'])){
    $HOST_NAME = 'localhost';
    $USER_NAME = 'multinote';
    $USER_PASSWORD = 'multinotepassword';
    $DB_NAME = 'multinote';

    $result = array();

    $db = new Dbase();
    $res = $db->connect($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME);
    if(is_bool($res)){
        $result['connection'] = true;
        $check = $db->check_login($_POST['reg_login']);
        $check2 = $db->check_email($_POST['reg_email']);
        if($check===false and $check2===false){
                $res2 = $db->add_user($_POST['reg_login'], $_POST['reg_password'], $_POST['reg_email']);
                if($res2===true){
                        $result['answer'] = 'Successful';
                } else{
                        $result['answer'] = "Not Successful";
                }
        } else if ($check===true){
                $result['answer'] = 'Login already exists';
        } else if($check2===true) {
                $result['answer'] = 'Email alreadu exists';
        } else {
                $result['answer'] = 'No access to DB';
        }
    }
    echo json_encode($result, JSON_FORCE_OBJECT);
} else {
  echo json_encode(["answer" => "Error"], JSON_FORCE_OBJECT);
}
?>

