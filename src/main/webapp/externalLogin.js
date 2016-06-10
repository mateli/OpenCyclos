function resolveCyclosURL() {
	var scripts = document.getElementsByTagName("script");
	for (var i = 0; i < scripts.length; i++) {
		var script = scripts.item(i);
		var pos = script.src.indexOf("/externalLogin.js");
		if (pos > 0) {
			return script.src.substring(0, pos);
		}
	}
	alert("Error determining script for /externalLogin.js")
}

function includeCyclosLogin(container, afterLogout) {
	if (typeof(container) == 'string') {
		container = document.getElementById(container);
	}
	var params = afterLogout ? "?afterLogout=" + encodeURIComponent(afterLogout) : '';
	var iframe = document.createElement('iframe');
	var url = resolveCyclosURL() + "/do/externalLogin" + params
	iframe.src = url;
	iframe.frameborder = 0;
	iframe.style.border = 'none';
	iframe.style.width = "100%";
	iframe.style.height = "100%";
	container.innerHTML = '';
	top.cyclosLoginIFrame = container.appendChild(iframe);
}
