$(document).ready(function(){
    $('.nav-item2').mouseenter(function(){
    	$('.nav-item2-menu2').stop().slideDown(600);
    });
    
    $('.nav-item2').mouseleave(function(){
    	$('.nav-item2-menu2').stop().slideUp(300);
    });
});

