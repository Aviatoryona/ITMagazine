<?php

namespace base;

interface PhotosI {

    function add($params);

    function update($photo);

    function getAll();

    function getByUserId($userId);

    function getById($id);
}
