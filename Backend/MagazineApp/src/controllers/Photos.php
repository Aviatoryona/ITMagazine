<?php

namespace controller;

class Photos extends \base\Base implements \base\PhotosI {

    public function add($params) {
        $desc = self::Av_sanitize($params['desc']);
        $user_id = $params['user_id'];
        $res = $this->uploadImage($params['img']);
        if ($res['error']) {
            return $res;
        }

        $name = $res['data'];
        $sql = "INSERT INTO photos(`desc`,url,user_id) VALUES('$desc','$name','$user_id')";

        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message'], $res);
    }

    public function getAll() {
        $sql = "SELECT * FROM photos ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    public function getById($id) {
        $sql = "SELECT * FROM photos WHERE  id='$id'";
        $res = self::Av_GetRowDataDB($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    public function getByUserId($userId) {
        $sql = "SELECT * FROM photos WHERE user_id='$userId' ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    public function update($photo) {
        $status = $photo['status'];
        $id = $photo['id'];
        $sql = "UPDATE photos SET status='$status' WHERE id= '$id'";
        return self::Av_ExecSql($sql, "Done");
    }

}
