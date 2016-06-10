Behaviour.register({

	'#naturesSelect': function(select) {
		select.onchange = function() {
			this.form.submit();
		}
	},

	'#groupFiltersSelect': function(select) {
		select.onchange = function() {
			this.form.submit();
		}
	},

	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroup";
		}
	},
	
	'img.groupDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGroup?groupId=" + img.getAttribute("groupId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeGroup?groupId=" + img.getAttribute("groupId");
			}
		}
	},
	
	'img.permissions': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGroupPermissions?groupId=" + img.getAttribute("groupId");
		}
	}
});