Behaviour.register({
	
	'a.elementProfileLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			var nature = a.getAttribute("nature");
			var id = a.getAttribute("elementId");
			var path, paramName;
			switch (nature) {
				case "ADMIN":
					path = "/adminProfile";
					paramName = "adminId";
					break;
				case "MEMBER":
					path = "/profile";
					paramName = "memberId";
					break;
				case "OPERATOR":
					path = "/operatorProfile";
					paramName = "operatorId";
					break;
			}
			self.location = pathPrefix + path + "?" + paramName + "=" + id;
		}
	},
	
	'img.disconnect': function(img) {
		setPointer(img);
		img.title = disconnectTooltip;
		img.onclick = function() {
			self.location = pathPrefix + "/disconnectUserSession?sessionId=" + img.getAttribute("sessionId");
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/home";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var selected = getValue("nature");
			if (isEmpty(selected)) {
				checkAll("nature", true);
			}
		}
	}
});

function refresh() {
	var selected = getValue("nature");
	if (selected == null) {
		return;
	}
	var params;
	if (selected instanceof Array) {
		params = selected.map(function(p) {
			return "nature=" + p;
		}).join("&");
	} else {
		params = "nature=" + selected;
	}
	self.location = pathPrefix + "/listConnectedUsers?" + params;
}
// setInterval("refresh()", 30000); 