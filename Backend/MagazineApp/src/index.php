<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$res = json_encode($_POST);
echo $res;
$data = json_decode($res, true);
foreach ($data as $key => $value) {

    echo $value;
}
