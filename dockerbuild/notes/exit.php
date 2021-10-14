<?php
include_once('parseurl.php');
if(isset($_POST['exit']) and isset($_POST['user']) and isset($_POST['token'])){
    $url = my_parse_url('url.json')."app/getnotes.php";
    $data = ['command'=>$_POST['exit'], 'user'=>$_POST['user'], 'token'=>$_POST['token']];
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    $response = curl_exec($ch);
    curl_close($ch);
    header("Location: index.php");

}

?>
