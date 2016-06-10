Behaviour.register({
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			var params = $H();
			params.set("typeId", img.getAttribute("typeId"));
			params.set("memberId", memberId);
			self.location = pathPrefix + "/accountHistory?" + params.toQueryString();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				self.location = pathPrefix + "/profile?memberId=" + memberId;
			}
		}
	}
});