$( document ).ready(function(){
    $(".button-collapse").sideNav();
    $('.carousel').carousel();
    $(".dropdown-button1").dropdown( { hover: false } );
    checkBrowser();
    //carousel buttons
    $('#carrNext').on('click', ()=>{
        $('.carousel').carousel('next');

    });
    $('#carrPrev').on('click', ()=>{
        $('.carousel').carousel('prev');
    });
});

function checkBrowser() {
    let isChromium = window.chrome,
        winNav = window.navigator,
        vendorName = winNav.vendor,
        isOpera = winNav.userAgent.indexOf("OPR") > -1,
        isIEedge = winNav.userAgent.indexOf("Edge") > -1,
        isIOSChrome = winNav.userAgent.match("CriOS");

    let parallaxHome = document.getElementById('parallaxHome');
    let parallaxOver = document.getElementById('parallaxOver');
    let parallaxFaq = document.getElementById('parallaxFaq');
    let parallaxPraktisch = document.getElementById('parallaxPraktisch');
    let parallaxNieuws = document.getElementById('parallaxNieuws');
    let browser = navigator.userAgent.toLowerCase();

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

