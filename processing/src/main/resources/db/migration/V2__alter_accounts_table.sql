ALTER TABLE public.accounts
ALTER COLUMN user_id TYPE uuid USING user_id::uuid;
