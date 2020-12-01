<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once 'Aviator.php';

foreach (glob("./interfaces/*.php") as $filename) {
    include $filename;
}

include_once 'Base.php';
foreach (glob("./controllers/*.php") as $filename) {
    include $filename;
}

if (!isset($_POST['action']) && !isset($_GET['action'])) {
    die("Unknown action");
}

$action = isset($_POST['action']) ? $_POST['action'] : $_GET['action'];

$user = new \controller\User();
$advert = new \controller\Advert();
$story = new \controller\Story();
$photos = new \controller\Photos();

/**
 * @return json object
 */
function printData($data) {
    die(json_encode($data));
}

$params = $_POST;

#user
define("auth", 'auth');
define("register", 'register');
##user
#story
define("addstory", 'addstory');
define("getallstory", 'getallstory');
define("getstorybyid", 'getstorybyid');
define("getstorybystatus", 'getstorybystatus');
define("getstorybyuserid", 'getstorybyuserid');
define("getstorybypaid", 'getstorybypaid');
define("updatestory", 'updatestory');
##story
#advert
define("addadvert", 'addadvert');
define("getalladvert", 'getalladvert');
define("getadvertbyid", 'getadvertbyid');
define("getadvertbystatus", 'getadvertbystatus');
define("getadvertbyuserid", 'getadvertbyuserid');
define("getadvertbypaid", 'getadvertbypaid');
define("updateadvert", 'updateadvert');
##advert
#photos
define("addphotos", 'addphotos');
define("getallphotos", 'getallphotos');
define("getphotosbyid", 'getphotosbyid');
define("getphotosbystatus", 'getphotosbystatus');
define("getphotosbyuserid", 'getphotosbyuserid');
define("getphotosbypaid", 'getphotosbypaid');
define("updatephotos", 'updatephotos');
##photos

switch ($action) {
    case auth:
        printData($user->auth($params));
        break;

    case register:
        printData($user->register($params));
        break;

    case addstory:
        printData($story->add($params));
        break;
    case getallstory:
        printData($story->getAll());
        break;
    case getstorybyid:
        printData($story->getById($params['id']));
        break;
    case getstorybystatus:
        printData($story->getByStatus($params['status']));
        break;
    case getstorybyuserid:
        printData($story->getByUserId($params['user_id']));
        break;
    case getstorybypaid:
        printData($story->getByPaid($params['paid']));
        break;
    case updatestory:
        printData($story->update($params));
        break;

    case addadvert:
        printData($advert->add($params));
        break;
    case getalladvert:
        printData($advert->getAll());
        break;
    case getadvertbyid:
        printData($advert->getById($params['id']));
        break;
    case getadvertbystatus:
        printData($advert->getByStatus($params['status']));
        break;
    case getadvertbyuserid:
        printData($advert->getByUserId($params['user_id']));
        break;
    case getadvertbypaid:
        printData($advert->getByPaid($params['paid']));
        break;
    case updateadvert:
        printData($advert->update($params));
        break;

    case addphotos:
        printData($photos->add($params));
        break;
    case getallphotos:
        printData($photos->getAll());
        break;
    case getphotosbyid:
        printData($photos->getById($params['id']));
        break;

    case getphotosbyuserid:
        printData($photos->getByUserId($params['user_id']));
        break;

    case updatephotos:
        printData($photos->update($params));
        break;

    default:
        break;
}
