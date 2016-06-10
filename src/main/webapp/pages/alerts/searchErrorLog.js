Behaviour.register({
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewErrorLogEntry?entryId=" + img.getAttribute("entryId");
		}
	}
});

Event.observe(self, "load", function() {
	getObject("query(period).begin").focus();
});