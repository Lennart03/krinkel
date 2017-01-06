$( document ).ready(function(){
    var carousel = $('.carousel');
    $(".button-collapse").sideNav();
    carousel.carousel();
    $(".dropdown-button1").dropdown( { hover: false } );
    checkBrowser();
    //carousel buttons
    $('#carrNext').on('click', function(){
        carousel.carousel('next');

    });
    $('#carrPrev').on('click', function(){
        carousel.carousel('prev');
    });
    $('body').keydown(function(e){
        var scrollPos = $(this).scrollTop();
        if(!(scrollPos > 800 && scrollPos < 2500)) return;
        switch (e.keyCode){
            case 37:
                carousel.carousel('prev');
                break;
            case 39:
                carousel.carousel('next');
                break;
            default:
                break;
        }
    });
    $('#sendBtn').on("click", function (){
        window.location = "mailto:info@krinkel.be?subject="+"Vraagje over krinkel: "+$('#subject').val()+"&body="+"Gesteld door: "+$('#name').val()
            +"%0D%0AReturn email: " + $('#email').val() + "%0D%0AVraag:%0D%0A"+$("#messageArea").val();
    });
});

function checkBrowser() {
    var isChromium = window.chrome,
        winNav = window.navigator,
        vendorName = winNav.vendor,
        isOpera = winNav.userAgent.indexOf("OPR") > -1,
        isIEedge = winNav.userAgent.indexOf("Edge") > -1,
        isIOSChrome = winNav.userAgent.match("CriOS");

    var parallaxHome = document.getElementById('parallaxHome');
    var parallaxOver = document.getElementById('parallaxOver');
    var parallaxFaq = document.getElementById('parallaxFaq');
    var parallaxPraktisch = document.getElementById('parallaxPraktisch');
    var parallaxNieuws = document.getElementById('parallaxNieuws');
    var browser = navigator.userAgent.toLowerCase();

    if ( isChrome() || browser.indexOf('firefox') > -1 ) {
        // is Google Chrome
        if (!!parallaxHome) {
            parallaxHome.style.backgroundAttachment = 'fixed';
            parallaxHome.style.backgroundPosition = '50% 25%';
        } else if (!!parallaxOver) {
            parallaxOver.style.backgroundAttachment = 'fixed';
            parallaxOver.style.backgroundPosition = '50% 60%';
        }
        else if(!!parallaxFaq){
            parallaxFaq.style.backgroundAttachment = 'fixed';
            parallaxFaq.style.backgroundPosition = '50% 60%';
        }
        else if (!!parallaxPraktisch) {
            parallaxPraktisch.style.backgroundAttachment = 'fixed';
            parallaxPraktisch.style.backgroundPosition = '50% 50%';
        }
        else if (!!parallaxNieuws) {
            parallaxNieuws.style.backgroundAttachment = 'fixed';
            parallaxNieuws.style.backgroundPosition = '50% 60%';
        }

    }

    function isChrome() {
        return isChromium !== null && isChromium !== undefined && vendorName === "Google Inc." && isOpera == false && isIEedge == false;
    }
}

$('.carousel.carousel-slider').carousel({full_width: true});

$('#landingPageMainImageScrollButtonAction').on('click', function(){
    $('html, body').animate({
        scrollTop: $('#landingPageInfoWrapper').offset().top - 40
    }, 1000); //tijd in ms
});
$('.scrollToSignup').on('click', function(){
    $('html, body').animate({
        scrollTop: $('#signup').offset().top - 40
    }, 1000); //tijd in ms
});


var lastScrollPos = 0;
$(window).scroll(function() {
    var scroll = $(window).scrollTop();
    var nav = $(".nav-wrapper");

    if (!(/*@cc_on!@*/false || !!document.documentMode)) {
        if ( scroll > lastScrollPos ) {
            nav.addClass("navUp");
        } else {
            nav.removeClass("navUp");
        }
        lastScrollPos = scroll;
    }
    if ( scroll >= $('.fixedHeader' ).height() - 100) {
        nav.removeClass("gradientNavbar");
        nav.addClass("plainNavbar");
    } else {
        nav.addClass("gradientNavbar");
        nav.removeClass("plainNavbar");
    }
});

function getTimeRemaining(endtime) {
    var t = Date.parse(endtime) - Date.parse(new Date());
    var seconds = Math.floor((t / 1000) % 60);
    var minutes = Math.floor((t / 1000 / 60) % 60);
    var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
    var days = Math.floor(t / (1000 * 60 * 60 * 24));
    return {
        'total': t,
        'days': days,
        'hours': hours,
        'minutes': minutes,
        'seconds': seconds
    };
}

function initializeClock(id, endtime) {
    var id = 'clockdiv';
    var endtime = deadline;

    var clock = document.getElementById(id);
    if (clock) {
        var daysSpan = clock.querySelectorAll('.days')[0];
        var hoursSpan = clock.querySelectorAll('.hours')[0];
        var minutesSpan = clock.querySelectorAll('.minutes')[0];
        var secondsSpan = clock.querySelectorAll('.seconds')[0];

        function updateClock() {
            var t = getTimeRemaining(endtime);

            daysSpan.innerHTML = t.days;
            hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
            minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
            secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);

            if (t.total <= 0) {
                clearInterval(timeinterval);
            }
        }

        updateClock();
        var timeinterval = setInterval(updateClock, 1000);
    }
}

function ready(fn) {
    if (document.readyState != 'loading'){
        fn();
    } else {
        document.addEventListener('DOMContentLoaded', fn);
    }
}

var deadline = new Date(Date.parse('Aug 25 2017'));
ready(initializeClock);