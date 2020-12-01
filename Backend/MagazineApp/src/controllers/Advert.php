<?php

namespace controller;

class Advert extends \base\Base implements \base\AdvertI {

    /**
     * @return object Description
     */
    public function add($params) {
        $title = self::Av_sanitize($params['title']);
        $desc = self::Av_sanitize($params['desc']);
        $user_id = $params['user_id'];

        $res = NULL;
        $img = "default.gif";

        if (isset($params['img'])) {
            $res = $this->uploadImage($params['img']);
            $img = ($res['error']) ? 'default.gif' : $res['data'];
        }

        $sql = "INSERT INTO `advert`(`title`, `desc`, `img`, `user_id`)"
                . " VALUES('$title','$desc','$img','$user_id')";
        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message'], $res);
    }

    /**
     *  @return array Description
     */
    public function getAll() {
        $sql = "SELECT * FROM `advert` ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }
        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return type Object
     */
    public function getById($id) {
        $sql = "SELECT * FROM advert WHERE  id='$id'";
        $res = self::Av_GetRowDataDB($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return array Description
     */
    public function getByPaid($paid) {
        $sql = "SELECT * FROM advert WHERE  paid='$paid' ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return array Description
     */
    public function getByStatus($status) {
        $sql = "SELECT * FROM advert WHERE  status='$status' ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return array Description
     */
    public function getByUserId($userId) {
        $sql = "SELECT * FROM advert WHERE  user_id='$userId' ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return Object Description
     */
    public function update($advert) {
        $id = $advert['id'];
        $title = self::Av_sanitize($advert['title']);
        $desc = self::Av_sanitize($advert['desc']);
        $status = $advert['status'];
        $paid = $advert['paid'];
        $position = $advert['position'];
        $sql = "UPDATE `advert` SET `title`='$title',`desc`='$desc',`status`='$status',"
                . "`paid`='$paid',`position`='$position' WHERE id='$id'";
        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message']);
    }

}
