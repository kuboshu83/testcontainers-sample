import time
from playwright.sync_api import sync_playwright

BASE_URL = "http://app-test:8080"


def test_crud():
    with sync_playwright() as p:
        api = p.request.new_context(base_url=BASE_URL)
        res = api.get("/api/users")
        assert not res.ok
        assert res.status == 500
