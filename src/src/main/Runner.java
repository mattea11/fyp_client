package main;

import java.util.Random;

public class Runner {
	
	public void badLogin() {
		System.out.println("Bad login at: " + System.currentTimeMillis());
	}
	
	public void goodLogin() {
		System.out.println("Good login at: " + System.currentTimeMillis());
	}

	public void run(final Account account) {
		final Random rand = new Random();
		while(true){
			final int randomNumber = rand.nextInt(10);
			
			if (randomNumber < 7){
				this.badLogin();
				account.setBadLogins(account.getBadLogins() + 1);
				if(account.getBadLogins() >= 3){
					account.setLocked(true);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					account.setBadLogins(0);
					account.setLocked(false);
				}
			} else {
				this.goodLogin();
				account.setBadLogins(0);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	
	
	public static void main(String[] args) {
		final Runner m = new Runner();
		final Account account = m.new Account(false, 0);
		m.run(account);
	}
	
	public class Account{
		private boolean locked;
		private Integer badLogins;
		
		public Account(final boolean locked, final Integer badLogins) {
			super();
			this.locked = locked;
			this.badLogins = badLogins;
		}

		public boolean isLocked() {
			return locked;
		}

		public void setLocked(boolean locked) {
			if(locked){
				System.out.println("Account locked!");
			} else {
				System.out.println("Account unlocked!");
			}
			this.locked = locked;
		}

		public Integer getBadLogins() {
			return badLogins;
		}

		public void setBadLogins(Integer badLogins) {
			this.badLogins = badLogins;
		}
	}
}
