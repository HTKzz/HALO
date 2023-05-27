$(document).ready(function(){
    $('.nav-item').mouseenter(function(){
    	$('nav-item-inner').slideDown(600);
    });
    
    $('.nav-item').mouseleave(function(){
    	$('nav-item-inner').slideUp(300);
    });
});

