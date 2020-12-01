<?php

namespace base;

interface StoryI {

    function add($params);

    function update($story);

    function getAll();

    function getByStatus($status);

    function getByUserId($userId);

    function getByPaid($paid);

    function getById($id);
}
