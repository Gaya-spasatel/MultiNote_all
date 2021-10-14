
<?php
include_once("../DBasequery.php");
function test_token($user, $token){
            $string = file_get_contents("../login/data.json");
            $data = json_decode($string, true);
	    if(is_array($data)){
	        if(array_key_exists($user,$data )){
			if($data[$user]==$token){
				return true;
			}
	}
}
	return false;
}

function connect_to_db(){
	$HOST_NAME = 'localhost';
	$USER_NAME = 'multinote';
	$USER_PASSWORD = 'multinotepassword';
	$DB_NAME = 'multinote';

	$db = new Dbase();
	$res = $db->connect($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME);
	if($res==true) return $db;
	else return false;
}

$result = array();

if(isset($_GET['command']) and isset($_GET['token'])){
	if(test_token($_GET['user'], $_GET['token'])){
	$result['answer'] = 'inget';	
	}
}

if(isset($_POST['command'])and isset($_POST['token'])){
	if(test_token($_POST['user'], $_POST['token'])){
	if($_POST['command']=='getallnotes'){
    		$db=connect_to_db();
		if($db!=false){
			$notes = $db->get_notes($_POST['user']);
			if(is_array($notes)){
				$result['notes'] = $notes;
				$result['answer'] = 'OK';
			} else {
				$result['answer'] = "DB Error";
			}
		}else{
			$result['answer'] = 'Connection error';
		}
	
	}else if($_POST['command']=='getnote'){
                $db = connect_to_db();
		if($db!=false){
			$note = $db->get_note_by_id($_POST['id_note']);
			if(is_array($note)){
                                $result['note'] = $note;
                                $result['answer'] = 'OK';
                        } else {
                                $result['answer'] = "DB Error";
                        }
			
		}else{
                        $result['answer'] = 'Connection error';
                }

	}else if($_POST['command']=='change'){
		$db = connect_to_db();
		if($db!=false){
			$ask = $db->is_modified($_POST['id_note']);
			if($ask==false){
				$ask = $db->set_is_modified($_POST['id_note'], true);
				if($ask==true){
					$result['answer'] = 'OK';
					$result['change'] = true;
				}else{
					$result['answer'] = 'Error';
				}
			}else{
				$result['answer'] = 'OK';
				$result['change'] = false;
			}
		}else{
			$result['answer'] = 'Connection error';
		}		
	}else if($_POST['command']=='save'){
		$db = connect_to_db();
		if($db!=false){
			$ask = $db->update_note($_POST['id_note'], $_POST['text'], $_POST['user']);
			if($ask==true){
				$result['answer'] = 'OK';	
			}else{
				$result['answer'] = 'Error';	
			}
			$ask = $db->set_is_modified($_POST['id_note'], false);
		}		
	}else if($_POST['command']=='new'){
		$db=connect_to_db();
		if($db!=false){
			$ask=$db->add_note($_POST['user']);
			if($ask==true){
				$result['answer'] = 'OK';
			}else{
				$result['answer'] = 'Error';
			}	
		}	
	} else if($_POST['command']=='exit'){
		$db=connect_to_db();
		if($db!=false){
			//delete user from data.json
			$string = file_get_contents("data.json");
			$data = json_decode($string, true);
			unset($data[$_POST['user']]);
			$json = json_encode($data);
			$file = fopen('data.json', 'w');
			$write = fwrite($file,$json);
			fclose($file);
			if($write){
				$result['answer'] = 'OK';
			} else{
				$result['answer'] = 'Error';
			}
		}else{
			$result['answer'] = 'Connection error';
		}
	} else if($_POST['command']=='listaccess'){
		$db = connect_to_db();
		if($db!=false){
			$ask=$db->get_list_access($_POST['id_note']);
			if(is_array($ask)){
				$result['list_access'] = $ask;
			}else{
				$result['answer'] = 'Error';
			}
		} else{
			$result['answer'] = 'Connection error';
		}
	}else if($_POST['command']=='addaccess'){
		$db= connect_to_db();
		if($db!=false){
			$ask = $db->check_login($_POST['login']);
			$ask2 = $db->check_access($_POST['id_note'], $_POST['login']);
			if($ask==true and $ask2==false){
				$res = $db->add_access($_POST['id_note'], $_POST['login']);
				if($res){
					$result['answer'] = 'OK';
				}else{
					$result['answer'] = 'Connection DB Error';
				}
			}else{
				$result['answer'] = 'data error';
			}
		}else{
			$result['answer'] = 'Connection error';
		}
	}
} else {
	$result['answer'] = 'Error';
}}
echo json_encode($result, JSON_FORCE_OBJECT | JSON_UNESCAPED_UNICODE);

?>
