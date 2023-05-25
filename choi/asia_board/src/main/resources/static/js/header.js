$(document).ready(function(){

    $('.nav-item2').mouseenter(function(){
    	$('.nav-item2-menu2').stop().slideDown(600);
    });
    
    $('.nav-item2').mouseleave(function(){
    	$('.nav-item2-menu2').stop().slideUp(300);
    });
    
    $('.nav-item3').mouseenter(function(){
    	$('.nav-item3-menu3').stop().slideDown(600);
    });
    
    $('.nav-item3').mouseleave(function(){
    	$('.nav-item3-menu3').stop().slideUp(300);
    });
});

