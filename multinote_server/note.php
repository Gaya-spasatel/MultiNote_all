<html>

<head><title>MultiNoteWebApp</title></head>
<body>

<?php
include_once('parseurl.php');
function echo_access()
{

    $data = ['command' => 'listaccess', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note']];
    $url = my_parse_url('url.json')."app/getnotes.php";
    $response = get_post($data, $url);
    $data = json_decode($response, true);
    //var_export($data);
    echo "<b>Has access</b><br><ol>";
    if (is_array($data) and isset($data['list_access'])) {
        foreach ($data['list_access'] as $access) {
            echo "<li>" . $access['access_login'] . "</li>";
        }
    }
    echo "</ol>";
    echo "<form method='post' action='note.php'>
	<input type='text' name='login'>
	<button name='button'>Add new access</button>
	<input type='hidden' name='token' value=" . $_POST['token'] . ">
        <input type='hidden' name='user' value=" . $_POST['user'] . ">
        <input type='hidden' name='id_note' value=" . $_POST['id_note'] . ">
        <input type='hidden' name='command' value='addaccess'>
	
	</form>";

}

function echo_form($data)
{
    //shows form with button change and access
    echo "Owner: ".$data['note']['owner']." Status is modified ". $data['note']['is_modified'];
    echo "<form method='post' action=note.php>
                        <textarea name='text' readonly>" . $data['note']['text'] . "</textarea>
                        <button name='button'>Change</button>
                        <input type='hidden' name='token' value=" . $_POST['token'] . ">
                        <input type='hidden' name='user' value=" . $_POST['user'] . ">
                        <input type='hidden' name='id_note' value=" . $_POST['id_note'] . ">
                        <input type='hidden' name='command' value='change'>
                </form>";
}

function get_post($data, $url)
{

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    $response = curl_exec($ch);
    curl_close($ch);
    return $response;

} ?>

<script>

    window.onbeforeunload = closingCode;

    function closingCode() {
        alert('hello!');
        <?php

        if(isset($_POST['token']) and isset($_POST['id_note']) and isset($_POST['user']) and isset($_POST['command']) and $_POST['command'] == 'change'){
        ?>
        let formData = new FormData(document.forms.form);
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/article/xmlhttprequest/post/user", true);
        xhr.send(formData);
        <?php
        }

        ?>
        return null;
    }

</script>

<?php
if (isset($_POST['token']) and isset($_POST['id_note']) and isset($_POST['user'])) {
    if (isset($_POST['command']) and $_POST['command'] == 'change') {
        $data = ['command' => 'change', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note']];
        $url = my_parse_url('url.json').'app/getnotes.php';
        $response = get_post($data, $url);
        $data = json_decode($response, true);
        if (is_array($data)) {
            echo "here";
            if (isset($data['change'])) {
                if ($data['change'] == true) {
                    echo "<form method='post' name='form' action=note.php>
                        <textarea name='text'>" . $_POST['text'] . "</textarea>
                        <button name='button'>Save</button>
                        <input type='hidden' name='token' value=" . $_POST['token'] . ">
                        <input type='hidden' name='user' value=" . $_POST['user'] . ">
                        <input type='hidden' name='id_note' value=" . $_POST['id_note'] . ">
                        <input type='hidden' name='command' value='save'>
                </form>";
                } else {
		 echo "<form method='get' action=app.php>
<input type='hidden' name='token' value=" . $_POST['token'] . ">
<input type='hidden' name='user' value=" . $_POST['user'] . ">
<button>Back to Menu</button></form>";

                    $data = ['command' => 'getnote', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note']];
                    $url = my_parse_url('url.json').'app/getnotes.php';
                    $response = get_post($data, $url);
                    $data = json_decode($response, true);
                    //var_export($response);
                    if (is_array($data)) {
                        echo_form($data);
                        echo_access();
                    } else {
                        echo "Wrong response1";
                    }

                }
            } else {
                echo "Wrong response3";
            }
        } else {
            echo "Wrong response2";
        }

    } else if (isset($_POST['command']) and $_POST['command'] == 'save') {

        $data = ['command' => 'save', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note'], 'text' => $_POST['text']];
        $url = my_parse_url('url.json').'app/getnotes.php';
        $response = get_post($data, $url);
        $data = json_decode($response, true);
        header("Location: app.php?token=" . $_POST['token'] . '&user=' . $_POST['user']);


    }else if(isset($_POST['command']) and $_POST['command'] =='addaccess'){
	 echo "<form method='get' action=app.php>
<input type='hidden' name='token' value=" . $_POST['token'] . ">
<input type='hidden' name='user' value=" . $_POST['user'] . ">
<button>Back to Menu</button></form>";


        $data = ['command' => 'addaccess', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note'], 'login' => $_POST['login']];
        $url = my_parse_url('url.json').'app/getnotes.php';
        $response = get_post($data, $url);
        $data = json_decode($response, true);
	var_export($data);
	$data = ['command' => 'getnote', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note']];
        $url = 'https://mail.mioks.ru/notes/app/getnotes.php';
        $response = get_post($data, $url);
        $data = json_decode($response, true);
        //var_export($response);
        if (is_array($data)) {
            echo_form($data);
            echo_access();
        } else {
            echo "Wrong response1";
        }
	

    } else {
	 echo "<form method='get' action=app.php>
<input type='hidden' name='token' value=" . $_POST['token'] . ">
<input type='hidden' name='user' value=" . $_POST['user'] . ">
<button>Back to Menu</button></form>";

        $data = ['command' => 'getnote', 'token' => $_POST['token'], 'user' => $_POST['user'], 'id_note' => $_POST['id_note']];
        $url = my_parse_url('url.json').'app/getnotes.php';
        $response = get_post($data, $url);
        $data = json_decode($response, true);
        //var_export($response);
        if (is_array($data)) {
            echo_form($data);
            echo_access();
        } else {
            echo "Wrong response1";
        }

    }
} else {
    echo "Wrong response";
}
?>
</body>
</html>
