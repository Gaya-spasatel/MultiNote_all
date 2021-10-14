<html>
<head><title>MultiNoteWebApp</title></head>
<body>
<?php
$flag = true;
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

if(isset($_POST['login'])){
    $data = ['login' => $_POST['login'], 'password' => $_POST['password']];
    $url = my_parse_url('url.json').'login/index.php';

    $response = get_post($data, $url);
    $result = json_decode($response, true);
    if(isset($result['answer'])){
	echo "init";
	
        if($result['answer']=="Autorized"){
            $flag = false;
            header("Location: app.php?token=".$result['token'].'&user='.$_POST['login']);
        } else{
	    echo $result['answer'];
	}

    } else{
        echo "Responce error";
    }

}else if(isset($_POST['reg_login'])){
	$data =  ['reg_login' => $_POST['reg_login'], 'reg_password' => $_POST['reg_password'], 'reg_email'=>$_POST['reg_email']];
    $url = my_parse_url('url.json').'login/register.php';
    $response = get_post($data, $url);

    $result = json_decode($response, true);
    var_export($result);
    if(isset($result['answer'])){
    	echo "Registration answer: ".$result['answer']."<br>";	
    }
	
}
if($flag){
echo "<table><tr><td>   
<form id='forma' action='index.php' method='post'> 
<h1>Форма входа</h1> 
<p>Заполните поля для входа на сайт</p> 
<p>Логин<br /><input type='text' name='login'></p> 
<p>Пароль<br /><input type='password' name='password'></p> 
<p><input type='submit' name='submit' value='Войти'> <br></p></form>
</td>
<td>
<form id='forma2' action='index.php' method='post'>
<h1>Форма Регистрации</h1>
<p>Заполните поля для регистрации на сайт</p>
<p>Логин<br /><input type='text' name='reg_login'></p>
<p>Пароль<br /><input type='password' name='reg_password'></p>
<p>Электронная почта<br /><input type='email' name='reg_email'></p>
<p><input type='submit' name='submit1' value='Зарегистрироваться'> <br></p></form>

</td>
</tr>";
}
?>
</body></html>
