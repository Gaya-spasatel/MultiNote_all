<?php
include_once('serverErrors.php');

class Dbase
{
    /*
    private $HOST_NAME = 'localhost';
    private $USER_NAME = 'multinote';
    private $USER_PASSWORD = 'multinotepassword';
    private $DB_NAME = 'multinote';
    */
    private $mysqli;

    public function connect($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME)
    {
        $this->mysqli = new mysqli($HOST_NAME, $USER_NAME, $USER_PASSWORD, $DB_NAME);
        $this->mysqli->set_charset("utf8");
        if ($this->mysqli->connect_errno) {
            return "Не удалось подключиться к MySQL: " . $this->mysqli->connect_error;
        }
        return true;
    }

    public function __destruct()
    {
        $this->mysqli->close();
    }

    public function check_password($login, $password)
    {
        /*
        * function checks users password by login and return true of false or
         * */

        if ($res = $this->mysqli->query("SELECT login, password FROM users WHERE login = '$login'")) {
            if ($res->num_rows == 0) {
                return false;
            }
            while ($row = $res->fetch_assoc()) {
                if ($row['password'] == $password) {
                    return true;
                }
            }
            $res->close();
            return false;
        }
        return "Ошибка запроса к БД функции проверки пароля";
    }

    public function add_user($login, $password, $email)
    {
        /*
         * function adds new user to database*/
        if ($res = $this->mysqli->query("INSERT INTO users SET login='$login', password='$password', email='$email'")) {
            $temp = $this->check_login($login);
            if (is_bool($temp) and $temp) {
                return true;
            }
            return false;
        }
        return "Ошибка запроса к БД функции добавление нового пользователя";
    }

    public function add_note($login)
    {
        /*function ads new note to db*/
        if ($res = $this->mysqli->query("INSERT INTO notes SET owner='$login', text='0', login_modified='$login', is_modified='0'")) {
            return true;

        }
        return "Ошибка запроса к БД функции добавление нового пользователя";

    }

    public function delete_user($login)
    {
        /*
         * function deletes user by his login
         */
        if ($res = $this->mysqli->query("DELETE FROM users WHERE login='$login'")) {
            $temp = $this->check_login($login);
            if (is_bool($temp) and !$temp) {
                return true;
            }
            return false;
        }
        return "Ошибка запроса к БД функции удаления пользователя";
    }

    public function check_login($login)
    {
        /*
         * checks if login exists already*/
        if ($res = $this->mysqli->query("SELECT * FROM users WHERE login = '$login'")) {

            if ($res->num_rows == 0) {
                return false;
            }
            return true;
        }
        return "Ошибка запроса к БД функции проверки логина";

    }

    public function check_email($email)
    {
        /*
         * checks if email exists already*/

        if ($res = $this->mysqli->query("SELECT * FROM users WHERE email = '$email'")) {

            if ($res->num_rows == 0) {
                return false;
            }
            return true;
        }
        return "Ошибка запроса к БД функции проверки электронной почты";
    }

    public function get_notes_user_has_access($login)
    {

        /*
         * gets all notes user has access to
         * //gets notes has access to
        //false or array are ok
         */
        $result = array();
        $access_login_array = $this->get_notes_access($login);
        if (gettype($access_login_array) == "string") {
            return $access_login_array;
        }
        if ($access_login_array) {
            foreach ($access_login_array as $row) {
                $temp = $this->get_note_by_id($row['id_note']);
                if (gettype($temp) != "array") {
                    return $temp;
                }
                $result[] = $temp;
            }
            return $result;
        }
        return false;
    }

    public function get_notes($login)
    {
        /*
         * gets users notes and notes that user has access to else false if there is any error
         * return array or string about error*/

        $returnResult = array(); //initialise empty array
        $temp = $this->get_notes_user_has_access($login);
        if (is_string($temp)) {
            return $temp;
        }
        if (is_array($temp)) {
            $returnResult += $temp;
        }

        //gets all user`s notes
        if ($res = $this->mysqli->query("SELECT * FROM notes WHERE owner='$login'")) {
            if ($res->num_rows != 0) {
                while ($row = $res->fetch_assoc()) {
                    $returnResult[] = $row;
                }
                $result[] = $returnResult;
            }
            $res->close();
        } else {
            return "Ошибка запроса к БД функции получения всех заметок пользователя";
        }
        return $returnResult;
    }

    public function get_note_by_id($id)
    {
        /*
         * gets note by it`s id returns false if there is no note with this id of asscoc list note
         */
        if ($res = $this->mysqli->query("SELECT * FROM notes WHERE id='$id'")) {
            if ($res->num_rows == 0) {
                return false;
            }
            while ($row = $res->fetch_assoc()) {
                return $row;
            }
            return false;
        }
        return "Ошибка запроса к БД функции получения заметки по айди";
    }

    public function get_notes_access($login)
    {
        /*
         * gets list of id`s notes user has
         */
        $returnResult = array(); //initialise empty array

        if ($res = $this->mysqli->query("SELECT id_note FROM access WHERE access_login='$login'")) {
            if ($res->num_rows == 0) {
                $res->close();
                return false;
            } else {
                while ($row = $res->fetch_assoc()) {
                    $returnResult[] = $row;
                }
                $res->close();
                return $returnResult;
            }

        }
        return "Ошибка запроса к БД функции получения доступа";
    }

    public function get_list_access($id_note){
        $returnResult = array(); //initialise empty array

        if ($res = $this->mysqli->query("SELECT access_login FROM access WHERE id_note='$id_note'")) {
                while ($row = $res->fetch_assoc()) {
                    $returnResult[] = $row;
                }
                $res->close();
                return $returnResult;
        }
        return "Ошибка запроса к БД функции получения доступа";
    }

    public function is_modified($id_note)
    {
        /*
         * return notes status about modifying now*/
        if ($res = $this->mysqli->query("SELECT is_modified FROM notes WHERE id='$id_note'")) {
            if ($res->num_rows == 0) {
                $res->close();
                return false;
            } else {
                $row = $res->fetch_assoc();
                $res->close();
                return $row['is_modified'];
            }

        }
        return "Ошибка запроса к БД функция получения статуса заметки";
    }

    public function set_is_modified($id_note, $status)
    {
        /*
         * changes status of note when it is started to modified*/
        if ($res = $this->mysqli->query("UPDATE notes SET is_modified='$status' WHERE id='$id_note'")) {

            if ($this->is_modified($id_note) == $status) {
                return true;
            }
            return false;
        }
        return "Ошибка запроса к БД изменения статуса редактирования заметки";
    }

    public function update_note($id_note, $text, $login_modified)
    {
        /*
         * updates note*/
        if ($res = $this->mysqli->query("UPDATE notes SET text='$text', last_modified=now(), login_modified='$login_modified' WHERE id='$id_note'")) {
            $temp = $this->get_note_by_id($id_note);
            if (is_array($temp)) {
                if ($temp['text'] == $text) {
                    return true;
                }
            }
            return false;
        }
        return "Ошибка запроса к БД функции обновления заметки";
    }

    public function check_access($id_note, $login)
    {
        /*returns if user has acces to this note*/
        if ($res = $this->mysqli->query("SELECT * FROM access WHERE access_login = '$login' and id_note='$id_note'")) {
            if ($res->num_rows == 0) {
                return false;
            }
            return true;
        }
        return "Ошибка запроса к БД функции проверки доступа";
    }

    public function add_access($id_note, $login)
    {
        /*add new access login exists already and note too*/
        if ($res = $this->mysqli->query("INSERT INTO access SET access_login='$login', id_note='$id_note'")) {
            $temp = $this->check_access($id_note, $login);
            if (is_bool($temp) and $temp) {
                return true;
            }
            return false;
        }
        return "Ошибка запроса к БД функции добавление нового пользователя";
    }
}

?>
