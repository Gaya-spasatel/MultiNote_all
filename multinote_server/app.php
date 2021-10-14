<html>
<head>
<title>MultiNoteWebApp</title>
</head>
<body>
<?php
include_once('parseurl.php');
function get_post($data, $url){

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    $response = curl_exec($ch);
    curl_close($ch);
    return $response;

}

if(isset($_GET['token'])){
	echo "<form action=exit.php method='post'><button name='exit'>Exit</button>
<input type='hidden' name='token' value=".$_GET['token'].">
<input type='hidden' name='user' value=".$_GET['user'].">
</form>";

	if(isset($_GET['command'])){
		$data = ['token'=>$_GET['token'], 'command'=>'new', 'user'=>$_GET['user']];
        	$url = my_parse_url('url.json')."app/getnotes.php";
        	$response = get_post($data, $url);
        	$data = json_decode($response, true);
		//var_export($data);
	}
	$data = ['token'=>$_GET['token'], 'command'=>'getallnotes', 'user'=>$_GET['user']];
	$url = my_parse_url('url.json')."app/getnotes.php";
	$response = get_post($data, $url);
	$data = json_decode($response, true);
	echo "<h2>My Notes</h2>";
	echo '<table><tr><th><b>Время</b></th><th><b>Владелец</b></th><th><b>Текст</b></th></tr></tr>';
	if(isset($data['answer'])){
	if($data['answer']=="OK"){
	    foreach($data['notes'] as $note){
		echo '<tr>
			<td>'.$note['last_modified'].'</td>
			<td>'.$note['owner'].'</td>
			<td>'.substr($note['text'], 0, 20).'</td>
			<td><form method="post" action="note.php">
				<button>Open</button>
				<input type="hidden" value="'.$_GET['token'].'" name="token">
				<input type="hidden" value="'.$note["id"].'" name="id_note">
				<input type="hidden" value="'.$_GET['user'].'" name="user">
			</form></td></tr>';		
	}}else{
	echo "<br>".$data['answer'];
}
echo "<form name='form' method='get' action=app.php>
	<button>+</button>
	<input type='hidden' value=".$_GET['user']." name='user'>
	<input type='hidden' value='new' name='command'>
	<input type='hidden' value=".$_GET['token']." name='token'>	
</form>";
}
}
?>
</body>
</html>

