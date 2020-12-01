<?php

namespace controller;

class User extends \base\Base implements \base\UserI {

    public function auth($params) {
        $email = $params['email'];
        $pwd = $params['pwd'];
        $sql = "SELECT * FROM users WHERE email='$email' AND pwd='$pwd' LIMIT 1";
        $res = self::Av_GetRowDataDB($sql);

        return !is_null($res) ? $this->getMessageModel(FALSE, 'Success', $res) :
                $this->getMessageModel(TRUE, 'Incorrect credentials', $res);
    }

    public function register($params) {
        $name = $params['name'];
        $email = $params['email'];
        $pwd = $params['pwd'];
        $category = $params['category'];

        $sql = "INSERT INTO users(name,email,pwd,category) VALUES('$name','$email','$pwd','$category')";
        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message']);
    }

}
