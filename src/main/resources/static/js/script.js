(function ($) {
	"use strict";
	// new WOW().init();
/*=============================================
	=    		 Preloader			      =
=============================================*/
// ========preloader===========
function preloader() {
	$('#preloader').delay(0).fadeOut("slow");
};

$(window).on('load', function () {
	preloader();
});
// =======Preloader-End========

// slider
// $('.your-class').slick({
// 	dots: true,
// 	arrows: false,
// 	infinite: true,
// 	speed: 300,
// 	slidesToShow: 1,
// 	adaptiveHeight: true,
// 	autoplay: true,
// 	autoplaySpeed: 2000
//   });
/*=============================================
	=     Menu sticky & Scroll to top      =
=============================================*/
$(window).on('scroll', function () {
	var scroll = $(window).scrollTop();
	if (scroll < 245) {
		$(".fly__cart").removeClass("open");
		$('.scroll-to-target').removeClass('open');
	} else {
		console.log("0");
		$(".fly__cart").addClass("open");
		$('.scroll-to-target').addClass('open');
	}
});


/*=============================================
	=    		 Scroll Up  	         =
=============================================*/
	$('.scroll-to-target').on('click', function () {
		var target = $(this).attr('data-target');
		// animate
		$('html, body').animate({
			scrollTop: $(target).offset().top
		}, 1000);
	});
// if ($('.scroll-to-target').length > 245) {
// 	$(".scroll-to-target").on('click', function () {
// 		console.log("Clicked");
// 		var target = $(this).attr('data-target');
// 		// animate
// 		$('html, body').animate({
// 			scrollTop: $(target).offset().top
// 		}, 1000);
//
// 	});
// }

})(jQuery);


// =======Brand-Slider=========
// $(document).ready(function() {
// 	$('.customer-logos').slick({
// 		slidesToShow: 3,
// 		slidesToScroll: 1,
// 		autoplay: true,
// 		infinite: true,
// 		autoplaySpeed: 1000,
// 		arrows: false,
// 		dots: false,
// 		pauseOnHover: false,
// 		responsive: [{
// 		breakpoint: 768,
// 		settings: {
// 			slidesToShow: 3
// 		}
// 		}, {
// 		breakpoint: 520,
// 		settings: {
// 			slidesToShow: 2
// 		}
// 		}]
// 	});
// });
// =======EndBrand-Slider=========

// ==========Mobile-Menu===========
// const navBar = document.getElementById("navbarNav");
// // const burger = document.querySelector(".burger");
// const mobile_menu = document.querySelector(".mobile__navigation__block");
// const close_mobile_menu = document.querySelector(".mobile__close__btn");
// const mobile_dark_bg = document.querySelector(".mobile__bg");
// const body = document.querySelector("body");

// mobileButton.addEventListener('click', () => {
//     navBar.classList.toggle("show")});

// burger.addEventListener('click', () => {
// 	mobile_menu.classList.add("mobile__navigation__block_active");
// 	mobile_dark_bg.classList.add("mobile__bg__active");
// 	body.style.overflow = "hidden";
// });
// close_mobile_menu.addEventListener('click', ()=>{
// 	mobile_menu.classList.remove("mobile__navigation__block_active");
// 	mobile_dark_bg.classList.remove("mobile__bg__active");
// 	body.style.overflow = "scroll";
// });
// mobile_dark_bg.addEventListener('click', ()=>{
// 	mobile_menu.classList.remove("mobile__navigation__block_active");
// 	mobile_dark_bg.classList.remove("mobile__bg__active");
// 	body.style.overflow = "scroll";
// });
// ==========End-Mobile-Menu===========

// ==================Light-Dark-Toggle-Large==============
// const checkbox = document.querySelector("#checkbox");
// const navbar = document.querySelector("header > .navbar-dark");
// mobile
// const checkbox__mobile = document.querySelector("#checkbox__mobile");
// checkbox__mobile.addEventListener('change', function () {
// 	if(!this.checked){
// 	  document.documentElement.setAttribute('data-theme', 'dark');
// 	  	body.classList.remove('light');
// 		body.classList.add("dark");
// 		navbar.classList.remove("navbar-light");
// 		navbar.classList.add("navbar-dark");
// 	  window.localStorage.setItem('data-theme', 'dark');
// 	  console.log(localStorage.getItem('data-theme'));
// 	} else {
// 	  document.documentElement.setAttribute('data-theme', 'light');
// 		body.classList.remove('dark');
// 		body.classList.add("light");
// 		navbar.classList.remove("navbar-dark");
// 		navbar.classList.add("navbar-light");
// 	  window.localStorage.setItem('data-theme', 'light');
// 	  console.log(localStorage.getItem('data-theme'));
// 	}
//   });

// var theme = window.localStorage.getItem('data-theme');
// if(theme) document.documentElement.setAttribute('data-theme', theme);
// checkbox.checked = theme == 'dark' ? false : true;
// checkbox__mobile.checked = theme == 'dark' ? false : true;
//
// if(!checkbox.checked){
// 	document.documentElement.setAttribute('data-theme', 'dark');
// 		body.classList.remove('light');
// 	  body.classList.add("dark");
// 	  navbar.classList.remove("navbar-light");
// 	  navbar.classList.add("navbar-dark");
// 	window.localStorage.setItem('data-theme', 'dark');
// 	console.log(localStorage.getItem('data-theme'));
//   } else {
// 	document.documentElement.setAttribute('data-theme', 'light');
// 	  body.classList.remove('dark');
// 	  body.classList.add("light");
// 	  navbar.classList.remove("navbar-dark");
// 	  navbar.classList.add("navbar-light");
// 	window.localStorage.setItem('data-theme', 'light');
// 	console.log(localStorage.getItem('data-theme'));
//   }
// //   mobile
// if(!checkbox__mobile.checked){
// 	document.documentElement.setAttribute('data-theme', 'dark');
// 		body.classList.remove('light');
// 	  body.classList.add("dark");
// 	  navbar.classList.remove("navbar-light");
// 	  navbar.classList.add("navbar-dark");
// 	window.localStorage.setItem('data-theme', 'dark');
// 	console.log(localStorage.getItem('data-theme'));
//   } else {
// 	document.documentElement.setAttribute('data-theme', 'light');
// 	  body.classList.remove('dark');
// 	  body.classList.add("light");
// 	  navbar.classList.remove("navbar-dark");
// 	  navbar.classList.add("navbar-light");
// 	window.localStorage.setItem('data-theme', 'light');
// 	console.log(localStorage.getItem('data-theme'));
//   }
// // ===mob-end
// checkbox.addEventListener('change', function () {
// 	if(!this.checked){
// 	  document.documentElement.setAttribute('data-theme', 'dark');
// 	  	body.classList.remove('light');
// 		body.classList.add("dark");
// 		navbar.classList.remove("navbar-light");
// 		navbar.classList.add("navbar-dark");
// 	  window.localStorage.setItem('data-theme', 'dark');
// 	  console.log(localStorage.getItem('data-theme'));
// 	} else {
// 	  document.documentElement.setAttribute('data-theme', 'light');
// 		body.classList.remove('dark');
// 		body.classList.add("light");
// 		navbar.classList.remove("navbar-dark");
// 		navbar.classList.add("navbar-light");
// 	  window.localStorage.setItem('data-theme', 'light');
// 	  console.log(localStorage.getItem('data-theme'));
// 	}
//   });


// ==================End-Light-Dark-Toggle-Large==============



// ==============Validation-Forms================
// Пример стартового JavaScript для отключения отправки форм при наличии недопустимых полей
(function () {
	'use strict'

	// Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
	var forms = document.querySelectorAll('.needs-validation')

	// Зацикливайтесь на них и предотвращайте отправку
	Array.prototype.slice.call(forms)
		.forEach(function (form) {
		form.addEventListener('submit', function (event) {
			if (!form.checkValidity()) {
			event.preventDefault()
			event.stopPropagation()
			}

			form.classList.add('was-validated')
		}, false)
		})
	})()
// ============End-Validation-Form===============

// =============Phone-Mask=======================
// window.addEventListener("DOMContentLoaded", function() {
// 	function setCursorPosition(pos, elem) {
// 		elem.focus();
// 		if (elem.setSelectionRange) elem.setSelectionRange(pos, pos);
// 		else if (elem.createTextRange) {
// 			var range = elem.createTextRange();
// 			range.collapse(true);
// 			range.moveEnd("character", pos);
// 			range.moveStart("character", pos);
// 			range.select()
// 		}
// 	}
//
// 	function mask(event) {
// 		var matrix = "+7 (___) ___ ____",
// 			i = 0,
// 			def = matrix.replace(/\D/g, ""),
// 			val = this.value.replace(/\D/g, "");
// 		if (def.length >= val.length) val = def;
// 		this.value = matrix.replace(/./g, function(a) {
// 			return /[_\d]/.test(a) && i < val.length ? val.charAt(i++) : i >= val.length ? "" : a
// 		});
// 		if (event.type == "blur") {
// 			if (this.value.length == 2) this.value = ""
// 		} else setCursorPosition(this.value.length, this)
// 	};
// 		var input1 = document.querySelector(".tel__mask1");
// 		var input2 = document.querySelector(".tel__mask2");
// 		var input3 = document.querySelector("#my-form > .tel__mask3");
//
// 		input1.addEventListener("input", mask, false);
// 		input1.addEventListener("focus", mask, false);
// 		input1.addEventListener("blur", mask, false);
// 		input2.addEventListener("input", mask, false);
// 		input2.addEventListener("focus", mask, false);
// 		input2.addEventListener("blur", mask, false);
// 	});
// ============End-Phone-Mask====================



