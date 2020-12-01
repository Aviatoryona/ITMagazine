<?php

namespace base;

abstract class Base extends \aviator\Aviator implements BaseI {

    /**
     * @param boolean $success
     * @param string $message
     * @param objecr $data
     * @return array
     */
    public function getMessageModel($success, $message, $data = '') {
        return array(
            "error" => $success,
            "message" => $message,
            "data" => $data
        );
    }

    /**
     * @param string $img
     * @return object
     */
    public function uploadImage($img) {
        $decodedImage = base64_decode($img);
        $name = self::Av_millitime() . self::GetRandomKey(6) . ".jpg";
        $return = file_put_contents("images/" . $name, $decodedImage);
        return $return === FALSE ?
                $this->getMessageModel(TRUE, "Failed", '') :
                $this->getMessageModel(FALSE, "Uploaded", $name);
    }

}
