<?php

namespace base;

interface BaseI {

    function getMessageModel($success, $message, $data);

    function uploadImage($img);
}
