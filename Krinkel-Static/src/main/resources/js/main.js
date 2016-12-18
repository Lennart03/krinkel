$( document ).ready(function(){
    let carousel = $('.carousel');
    $(".button-collapse").sideNav();
    carousel.carousel();
    $(".dropdown-button1").dropdown( { hover: false } );
    checkBrowser();
    //carousel buttons
    $('#carrNext').on('click', ()=>{
        carousel.carousel('next');

    });
    $('#carrPrev').on('click', ()=>{
        carousel.carousel('prev');
    });
    $('body').keydown((e)=>{
        let scrollPos = $(this).scrollTop();
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
    $('#sendBtn').on("click", ()=>{
        window.location = "mailto:info@krinkel.be?subject="+"Vraagje over krinkel: "+$('#subject').val()+"&body="+"Gesteld door: "+$('#name').val()
            +"%0D%0AReturn email: " + $('#email').val() + "%0D%0AVraag:%0D%0A"+$("#messageArea").val();
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
$('.scrollToSignup').on('click', function(){
    $('html, body').animate({
        scrollTop: $('#signup').offset().top - 40
    }, 1000); //tijd in ms
});

