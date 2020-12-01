<?php

namespace base;

interface AdvertI {

    function add($params);

    function update($advert);

    function getAll();

    function getByStatus($status);

    function getByUserId($userId);

    function getByPaid($paid);

    function getById($id);
}
