<?php

namespace controller;

class Story extends \base\Base implements \base\StoryI {

    /**
     * @return array Description
     */
    public function add($params) {
        $user_id = $params['user_id'];
        $title = self::Av_sanitize($params['title']);
        $desc = self::Av_sanitize($params['desc']);
        $sql = "INSERT INTO story(`user_id`, `title`, `desc`) VALUES('$user_id','$title','$desc')";
        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message']);
    }

    /**
     * @return array Description
     */
    public function getAll() {
        $sql = "SELECT * FROM `story` ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }
        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return array Description
     */
    public function getById($id) {
        $sql = "SELECT * FROM story WHERE  id='$id'";
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
        $sql = "SELECT * FROM story WHERE  paid='$paid' ORDER BY id DESC LIMIT 1000";
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
        $sql = "SELECT * FROM story WHERE  status='$status' ORDER BY id DESC LIMIT 1000";
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
        $sql = "SELECT * FROM story WHERE  user_id='$userId' ORDER BY id DESC LIMIT 1000";
        $res = self::Av_GetMultiRowsData($sql);
        if (!is_null($res)) {
            return $res;
        }

        return $this->getMessageModel(FALSE, "No data");
    }

    /**
     * @return object Description
     */
    public function update($story) {
        $id = $story['id'];
        $status = $story['status'];
        $paid = $story['paid'];
        $user_id = $story['user_id'];
        $title = self::Av_sanitize($story['title']);
        $desc = self::Av_sanitize($story['desc']);
        $photoids = $story['photoids'];
        $sql = "UPDATE `story` SET `status`='$status',`paid`='$paid',"
                . "`user_id`='$user_id',`title`='$title',`desc`='$desc',"
                . "`photoids`='$photoids' WHERE id='$id' ";
        $response = self::Av_ExecSql($sql, "Done");
        return $this->getMessageModel($response['error'], $response['message']);
    }

}
