Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editCustomizedFile?type=" + type;
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editCustomizedFile?fileId=" + img.getAttribute("fileId");
		}
	},
	
	'img.preview': function(img) {
		setPointer(img);
		var fileName = encodeURIComponent(img.getAttribute("fileName"));
		img.onclick = function() {
			if (type == "HELP") {
				showHelp(replaceAll(fileName, ".jsp", ""), 330, 400, '', -20, 10);
			} else {					
				var url = pathPrefix + "/showCustomizedFile?type=" + type + "&fileName=" + fileName;
				window.open(url, 'pop', 'scrollbars,resizable,width=620,height=500,top=10,left=10');
			}
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/stopCustomizingFile?fileId=" + encodeURIComponent(img.getAttribute("fileId"));
			}
		}
	}
});