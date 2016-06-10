Behaviour.register({
	
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
