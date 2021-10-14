<?php

function my_parse_url($filename){
	$string = file_get_contents($filename);
        $data = json_decode($string, true);
	if(is_array($data) and isset($data['url'])) return $data['url'];
	return false;
}

?>
