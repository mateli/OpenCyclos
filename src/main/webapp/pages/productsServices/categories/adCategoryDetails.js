Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	'.alterCurrentCategory': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?id=" + element.getAttribute("categoryId");
		}
	},
	'.listAdCategories': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/listAdCategories";
		}
	},
	'.view': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?id=" + element.getAttribute("categoryId");
		}
	},
	'.edit': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?id=" + element.getAttribute("categoryId");
		}
	},
	'.remove': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeAdCategory?id=" + element.getAttribute("categoryId");
			}
		}
	},
	'#newButton': function(button) {
		element.onclick = function() {
			self.location = pathPrefix + "/editAdCategory?parent=" + currentCategory;
		}
	},
	'#changeOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setAdCategoryOrder?currentCategory=" + currentCategory;
		}
	}
});

