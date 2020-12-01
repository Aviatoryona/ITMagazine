<?php

namespace base;

interface UserI {
    /*
     * @params array
     */

    function auth($params);

    /*
     * @params array
     */

    function register($params);
}
